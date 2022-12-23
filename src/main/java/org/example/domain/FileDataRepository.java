package org.example.domain;

import org.example.data.models.FileModel;

import java.util.ArrayList;

public interface FileDataRepository {

    void insertFile(FileModel fileModel);

    void updateFile(String id);

    ArrayList<FileModel> getAllFileOfADirectory(String directoryName);

    ArrayList<FileModel> getAllFiles();
}
