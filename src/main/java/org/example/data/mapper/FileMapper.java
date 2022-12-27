package org.example.data.mapper;

import org.example.data.models.FileModel;
import org.example.data.models.FileTableConstants;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class FileMapper implements RowMapper<FileModel>, FileTableConstants {
    @Override
    public FileModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        String id = rs.getString(id_field);
        String fileName = rs.getString(fileName_field);
        String filePath = rs.getString(filePath_field);
        String directoryPath = rs.getString(directoryPath_field);
        String status = rs.getString(status_field);
        String fileData = rs.getString(file_data_field);
        int numberOfRecords = rs.getInt(numberOfRecords_field);
        Timestamp insertTime = rs.getTimestamp(insertTime_field);
        Timestamp lastModifiedTime = rs.getTimestamp(lastModified_field);
        return new FileModel(
                id, fileName, fileData, filePath, directoryPath, numberOfRecords, insertTime, lastModifiedTime, status
        );
    }
}
