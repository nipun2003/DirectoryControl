package org.example.domain.repository;

import org.example.data.models.FileModel;

import java.util.ArrayList;

public interface FileDataRepository {

    void insertFile(FileModel fileModel);

    void updateFile(FileModel id);

    ArrayList<FileModel> getAllFileOfADirectory(String directoryName);

    ArrayList<FileModel> getAllFiles();
}
