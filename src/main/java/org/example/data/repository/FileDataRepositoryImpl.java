package org.example.data.repository;

import org.example.data.mapper.FileMapper;
import org.example.data.models.FileModel;
import org.example.data.models.FileTableConstants;
import org.example.domain.repository.FileDataRepository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class FileDataRepositoryImpl implements FileDataRepository, FileTableConstants {


    private final JdbcTemplate database;


    public FileDataRepositoryImpl(JdbcTemplate database) {
        this.database = database;
        try {

            /* Table columns order ID,fileName,fileData,filePath,
            directoryPath,numberOfRecords,insertTime,lastModifiedTime,status
             */
            String query = String.format(
                    "CREATE TABLE IF NOT EXISTS %s (" +
                            "%s TEXT PRIMARY KEY," +
                            "%s TEXT NOT NULL," +
                            "%s TEXT NOT NULL," +
                            "%s TEXT NOT NULL," +
                            "%s TEXT NOT NULL," +
                            "%s INT NOT NULL," +
                            "%s TIMESTAMP NOT NULL," +
                            "%s TIMESTAMP NOT NULL," +
                            "%s TEXT NOT NULL);",
                    tableName, id_field, fileName_field, file_data_field,
                    filePath_field, directoryPath_field, numberOfRecords_field,
                    insertTime_field, lastModified_field, status_field
            );
            this.database.execute(query);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean isFileAlreadyInserted(String id) {
        String query = "SELECT EXISTS(SELECT 1 FROM " + tableName + " WHERE " + id_field + "=?);";
        return Boolean.TRUE.equals(database.query(query, ps -> ps.setString(1, id),
                rs -> {
                    rs.next();
                    return rs.getBoolean(1);
                }));
    }


    @Override
    public boolean insertFile(FileModel fileModel) {
        boolean isInserted = false;
        try {
            if (isFileAlreadyInserted(fileModel.getId())) {
                return false;
            }
            System.out.println("Inserting");
            fileModel.readFile();
            // Query order fileId, fileName,fileData,filePath,directoryPath,numberOfRecords,insertTime,lastModifiedTime,status
            String query = String.format("INSERT INTO %s VALUES(?,?,?,?,?,?,?,?,?) ON CONFLICT DO NOTHING;", tableName);
            database.update(query, fileModel.getId(), fileModel.getFileName(), fileModel.getFileData(),
                    fileModel.getFilePath(), fileModel.getDirectoryPath(), fileModel.getNumberOfRecords(),
                    fileModel.getInsertTime(), fileModel.getLastModified(), fileModel.getStatus());
            isInserted = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return isInserted;
    }

    @Override
    public void updateFile(FileModel id) {

    }

    @Override
    public List<FileModel> getAllFileOfADirectory(String directoryName) {
        try {
            String query = "SELECT * FROM " + tableName + " WHERE " + directoryPath_field + "=?";
            return database.query(query, ps -> ps.setString(1, directoryName), new FileMapper());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return List.of();
        }
    }

    @Override
    public List<FileModel> getAllFiles() {
        try {
            String query = "SELECT * FROM " + tableName;
            return database.query(query, new FileMapper());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return List.of();
        }
    }

}
