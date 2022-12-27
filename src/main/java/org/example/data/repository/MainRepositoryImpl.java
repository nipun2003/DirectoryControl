package org.example.data.repository;

import org.example.data.models.FileModel;
import org.example.domain.repository.FileDataRepository;
import org.example.domain.repository.MainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class MainRepositoryImpl implements MainRepository {


    private final FileDataRepository fileDataRepository;

    public MainRepositoryImpl(FileDataRepository fileDataRepository) {
        this.fileDataRepository = fileDataRepository;
    }

    @Override
    public boolean processDirectory(String dataPath,String destinationPath) {
        try {
            ArrayList<File> files = getAllTextFileInsideADirectory(dataPath);
            int batch = 20;
            if (files.size() <= batch) {
                files.forEach(file -> {
                    FileModel fileModel = new FileModel(file, dataPath);
                    boolean inserted = fileDataRepository.insertFile(fileModel);
                    if (inserted) {
                        fileModel.copyFile(destinationPath);
                    }
                });
                return true;
            }
            insertFileInBatches(getAllTextFileInsideADirectory(dataPath), batch, dataPath, destinationPath);
            return true;
        } catch (IOException e) {
            System.out.println("Error reading directory path");
            return false;
        }
    }

    private void insertFileInBatches(ArrayList<File> files, int batch, String parent, String destination) {
        for (int i = 0; i < files.size(); i += batch) {
            int chunkSize = Math.min(files.size(), batch + i);
            insertFilesUsingThread(files.subList(i, chunkSize), parent, destination);
        }
    }

    private void insertFilesUsingThread(List<File> files, String parent, String destination) {
        Thread thread = new Thread(() -> files.forEach(file -> {
            FileModel fileModel = new FileModel(file, parent);
            boolean inserted = fileDataRepository.insertFile(fileModel);
            if (inserted) {
                fileModel.copyFile(destination);
            }
        }));
        thread.start();
    }

    @Override
    public List<FileModel> getAllFilesInsertedInDatabase() {
        return fileDataRepository.getAllFiles();
    }

    private ArrayList<File> getAllTextFileInsideADirectory(String directoryPath) throws IOException {
        ArrayList<File> arrayList = new ArrayList<>();
        try (Stream<Path> stream = Files.list(Paths.get(directoryPath))) {
            stream.forEach(path -> {
                // /root/home/nipun/demo
                // /root/home/nipun/demo/nipun.txt
                if (Files.isDirectory(path)) {
                    // We recursively check if there present another text file inside subdirectory
                    try {
                        arrayList.addAll(getAllTextFileInsideADirectory(path.toAbsolutePath().toString()));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    File file = path.toFile();
                    if (file.getName().endsWith(".txt")) {
                        arrayList.add(file);
                    }
                }
            });
            return arrayList;
        }
    }
}
