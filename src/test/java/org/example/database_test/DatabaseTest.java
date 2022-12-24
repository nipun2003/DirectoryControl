package org.example.database_test;

import org.example.data.database.PostgresDatabase;
import org.example.data.models.FileModel;
import org.example.data.repository.FileDataRepositoryImpl;
import org.example.domain.repository.FileDataRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Timestamp;

public class DatabaseTest {

    @Test
    void insertARowToDatabase(){
        FileDataRepository fileDataRepository = new FileDataRepositoryImpl(PostgresDatabase.getDatabase());
        Timestamp lastModified = new Timestamp(1001);
        FileModel fileModel = new FileModel("demo.txt",
                "This is demo data", "/root/d/demo.txt", "/root/d", 5, lastModified);
        System.out.println(fileModel);
    }
}
