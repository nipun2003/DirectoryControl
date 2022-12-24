package org.example.data.repository;

import org.example.data.models.FileModel;
import org.example.domain.repository.FileDataRepository;
import org.example.domain.repository.MainRepository;

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
    public boolean insertFileIntoDatabase(String dataPath) {
        try {
            getAllTextFileInsideADirectory(dataPath).forEach(file -> {
                FileModel fileModel = new FileModel(file);
                fileDataRepository.insertFile(fileModel);
            });
            return true;
        } catch (IOException e) {
            System.out.println("Error reading directory path");
            return false;
        }
    }

    @Override
    public List<FileModel> getAllFilesInsertedInDatabase() {
        return fileDataRepository.getAllFiles();
    }

    private ArrayList<File> getAllTextFileInsideADirectory(String directoryPath) throws IOException {
        ArrayList<File> arrayList = new ArrayList<>();
        try (Stream<Path> stream = Files.list(Paths.get(directoryPath))) {
            stream.forEach(path -> {
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
