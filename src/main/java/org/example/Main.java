package org.example;

import org.example.data.database.PostgresDatabase;
import org.example.data.models.FileModel;
import org.example.data.repository.FileDataRepositoryImpl;
import org.example.domain.FileDataRepository;

public class Main {
    public static void main(String[] args) {
        PostgresDatabase postgresDatabase = new PostgresDatabase(
                "directory_demo", "lucifer", "2003"
        );
        FileDataRepository fileDataRepository = new FileDataRepositoryImpl(postgresDatabase);
        FileModel fileModel = new FileModel(
                "test", "test/demo", 5, "directory/demo"
        );
        fileDataRepository.insertFile(fileModel);
    }
}