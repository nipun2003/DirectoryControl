package org.example.domain.repository;

import org.example.data.models.FileModel;

import java.util.ArrayList;
import java.util.List;

public interface FileDataRepository {

    boolean insertFile(FileModel fileModel);

    void updateFile(FileModel id);

    List<FileModel> getAllFileOfADirectory(String directoryName);

    List<FileModel> getAllFiles();
}
