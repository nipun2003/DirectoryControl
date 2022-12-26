package org.example.data.repository;

import org.example.data.database.PostgresDatabase;
import org.example.data.models.FileModel;
import org.example.domain.repository.FileDataRepository;

import java.sql.*;
import java.util.ArrayList;

public class FileDataRepositoryImpl implements FileDataRepository {

    private final String tableName = "fileSchema";
    private final String id_field = "ID";
    private final String filePath_field = "filePath";
    private final String fileName_field = "fileName";
    private final String directoryPath_field = "directoryPath";
    private final String numberOfRecords_field = "numberOfRecords";
    private final String insertTime_field = "insertTime";
    private final String lastModified_field = "lastModified";

    private final String status_field = "status";
    private final String file_data_field = "fileData";

    private final PostgresDatabase database;


    public FileDataRepositoryImpl(PostgresDatabase database) {
        this.database = database;
        try {
            Connection connection = database.connectToDatabase();
            Statement statement = connection.createStatement();

            // Table columns order ID,fileName,fileData,filePath,directoryPath,numberOfRecords,insertTime,lastModifiedTime,status
            String query = String.format(
                    "CREATE TABLE IF NOT EXISTS %s (%s TEXT PRIMARY KEY,%s TEXT NOT NULL,%s TEXT NOT NULL,%s TEXT NOT NULL,%s TEXT NOT NULL,%s INT NOT NULL,%s TIMESTAMP NOT NULL,%s TIMESTAMP NOT NULL,%s TEXT NOT NULL);", tableName, id_field, fileName_field, file_data_field, filePath_field, directoryPath_field, numberOfRecords_field, insertTime_field, lastModified_field, status_field
            );
            statement.execute(query);
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

    private boolean isFileAlreadyInserted(Connection connection, String id) {
        try {
            String query = "SELECT EXISTS(SELECT 1 FROM " + tableName + " WHERE " + id_field + "=?);";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getBoolean(1);
        } catch (SQLException e) {
            return false;
        }
    }


    @Override
    public boolean insertFile(FileModel fileModel) {
        Connection connection = database.connectToDatabase();
        if (connection == null) return false;
        boolean isInserted = false;
        try {
            if (isFileAlreadyInserted(connection, fileModel.getId())) {
                closeConnection(connection);
                return false;
            }
            System.out.println("Inserting");
            fileModel.readFile();
            // Query order fileId, fileName,fileData,filePath,directoryPath,numberOfRecords,insertTime,lastModifiedTime,status
            String query = String.format("INSERT INTO %s VALUES(?,?,?,?,?,?,?,?,?) ON CONFLICT DO NOTHING;", tableName);
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, fileModel.getId());
            statement.setString(2, fileModel.getFileName());
            statement.setString(3, fileModel.getFileData());
            statement.setString(4, fileModel.getFilePath());
            statement.setString(5, fileModel.getDirectoryPath());
            statement.setInt(6, fileModel.getNumberOfRecords());
            statement.setTimestamp(7, fileModel.getInsertTime());
            statement.setTimestamp(8, fileModel.getLastModified());
            statement.setString(9, fileModel.getStatus());
            statement.executeUpdate();
            statement.close();
            isInserted = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            closeConnection(connection);
        }
        return isInserted;
    }

    @Override
    public void updateFile(FileModel id) {

    }

    @Override
    public ArrayList<FileModel> getAllFileOfADirectory(String directoryName) {
        Connection connection = database.connectToDatabase();
        if (connection == null) return new ArrayList<>();
        ArrayList<FileModel> results = new ArrayList<>();
        try {
            String query = "SELECT * FROM " + tableName + " WHERE " + directoryPath_field + "=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, directoryName);
            ResultSet rs = statement.executeQuery();
            results = extractFileModelFromQuery(rs);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            closeConnection(connection);
        }
        return results;
    }

    @Override
    public ArrayList<FileModel> getAllFiles() {
        Connection connection = database.connectToDatabase();
        if (connection == null) return new ArrayList<>();
        ArrayList<FileModel> results = new ArrayList<>();
        try {
            String query = "SELECT * FROM " + tableName;
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            results = extractFileModelFromQuery(rs);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            closeConnection(connection);
        }
        return results;
    }

    private ArrayList<FileModel> extractFileModelFromQuery(ResultSet rs) {
        try {
            ArrayList<FileModel> arrayList = new ArrayList<>();
            while (rs.next()) {
                String id = rs.getString(id_field);
                String fileName = rs.getString(fileName_field);
                String filePath = rs.getString(filePath_field);
                String directoryPath = rs.getString(directoryPath_field);
                String status = rs.getString(status_field);
                String fileData = rs.getString(file_data_field);
                int numberOfRecords = rs.getInt(numberOfRecords_field);
                Timestamp insertTime = rs.getTimestamp(insertTime_field);
                Timestamp lastModifiedTime = rs.getTimestamp(lastModified_field);

                FileModel fileModel = new FileModel(
                        id, fileName, fileData, filePath, directoryPath, numberOfRecords, insertTime, lastModifiedTime, status
                );
                arrayList.add(fileModel);
            }
            return arrayList;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
