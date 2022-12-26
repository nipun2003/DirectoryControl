package org.example.domain.repository;

import org.example.data.models.FileModel;

import java.util.List;

public interface MainRepository {

    boolean processDirectory(String dataPath,String destinationPath);

    List<FileModel> getAllFilesInsertedInDatabase();
}
