package org.example.data.repository;

import org.example.data.database.PostgresDatabase;
import org.example.data.models.FileModel;
import org.example.domain.FileDataRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class FileDataRepositoryImpl implements FileDataRepository {

    private final String tableName = "fileSchema";
    private final String id = "ID";
    private final String filePath = "filePath";
    private final String fileName = "fileName";
    private final String directoryPath = "directoryPath";
    private final String numberOfRecords = "numberOfRecords";
    private final String insertTime = "insertTime";
    private final String lastModified = "lastModified";

    private PostgresDatabase database;


    public FileDataRepositoryImpl(PostgresDatabase database) {
        this.database = database;
        try {
            Connection connection = database.connectToDatabase();
            Statement statement = connection.createStatement();
            String query = String.format(
                    "CREATE TABLE IF NOT EXISTS %s (%s SERIAL PRIMARY KEY,%s TEXT NOT NULL,%s TEXT NOT NULL,%s TEXT NOT NULL,%s INT NOT NULL,%s TIMESTAMP NOT NULL,%s TIMESTAMP NOT NULL);", tableName, id, fileName, filePath, directoryPath, numberOfRecords, insertTime, lastModified
            );
            statement.execute(query);
            System.out.println("Table created " + tableName);
            statement.close();
            connection.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error closing connection");
        }
    }


    @Override
    public void insertFile(FileModel fileModel) {
        Connection connection = database.connectToDatabase();
        if (connection == null) return;
        try {

            // Query order fileName,filePath,directoryPath,numberOfRecords,insertTime,lastModifiedTime
            String query = String.format("INSERT INTO %s VALUES(DEFAULT,?,?,?,?,?,?);", tableName);
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,fileModel.getFileName());
            statement.setString(2,fileModel.getFilePath());
            statement.setString(3,fileModel.getDirectoryPath());
            statement.setInt(4,fileModel.getNumberOfRecords());
            statement.setTimestamp(5,fileModel.getInsertTime());
            statement.setTimestamp(6,fileModel.getLastModified());
            statement.executeUpdate();
            statement.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public void updateFile(String id) {

    }

    @Override
    public ArrayList<FileModel> getAllFileOfADirectory(String directoryName) {
        return null;
    }

    @Override
    public ArrayList<FileModel> getAllFiles() {
        return null;
    }
}
