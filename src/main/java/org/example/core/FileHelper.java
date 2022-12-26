package org.example.core;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class FileHelper {

    private String filePath;

    public FileHelper(String filePath) {
        this.filePath = filePath;
    }

    public List<String> getAllDataSeperatedWithSemicolon() {
        try {
            Path path = Paths.get(filePath);
            List<String> lines = Files.readAllLines(path);
            String str = String.join("", lines);
            return Arrays.stream(str.split(";")).toList();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }

    public void copyFile(String destination, String parent) throws IOException, FileAlreadyExistsException {
        File destinationDirectory = new File(destination);
        if (!destinationDirectory.exists()) destinationDirectory.mkdir();
        String destinationFileName = filePath.substring(parent.length() + 1);
        String destinationFile = getFile(destination, destinationFileName);
        Path sourcePath = Paths.get(filePath);
        Path destinationPath = Paths.get(destinationFile);
        Files.copy(sourcePath, destinationPath);
    }

    private String getFile(String directory, String fileName) {
        File file = new File(directory, fileName);
        while (file.exists()) {
            String name = file.getName();
            String temp = name.substring(0, name.lastIndexOf('.'));
            temp += "_n.txt";
            file = new File(directory, temp);
        }
        File newFileDirectory = new File(file.getParent());
        if (!newFileDirectory.exists()) newFileDirectory.mkdir();
        return file.getAbsolutePath();
    }
}
