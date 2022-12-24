package org.example.core;

import java.io.IOException;
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
}
