package org.example.data.models;

import java.sql.Timestamp;
import java.util.Date;

public class FileModel {

    private int id;
    private String fileName;
    private String filePath;
    private int numberOfRecords;

    private String directoryPath;
    private Timestamp insertTime;
    private Timestamp lastModified;

    public FileModel() {
    }

    public FileModel(String fileName, String filePath, int numberOfRecords, String directoryPath) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.numberOfRecords = numberOfRecords;
        this.directoryPath = directoryPath;
        Date date = new Date();
        this.insertTime =new Timestamp(date.getTime());
        this.lastModified = new Timestamp(date.getTime());
    }

    public FileModel(String fileName, String filePath, int numberOfRecords, String directoryPath, Timestamp lastModified) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.numberOfRecords = numberOfRecords;
        this.directoryPath = directoryPath;
        this.lastModified = lastModified;
    }

    public FileModel(int id, String fileName, String filePath, String directoryPath, int numberOfRecords, Timestamp insertTime, Timestamp lastModified) {
        this.id = id;
        this.fileName = fileName;
        this.filePath = filePath;
        this.numberOfRecords = numberOfRecords;
        this.insertTime = insertTime;
        this.lastModified = lastModified;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getNumberOfRecords() {
        return numberOfRecords;
    }

    public void setNumberOfRecords(int numberOfRecords) {
        this.numberOfRecords = numberOfRecords;
    }

    public Timestamp getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Timestamp insertTime) {
        this.insertTime = insertTime;
    }

    public Timestamp getLastModified() {
        return lastModified;
    }

    public void setLastModified(Timestamp lastModified) {
        this.lastModified = lastModified;
    }

    public String getDirectoryPath() {
        return directoryPath;
    }

    public void setDirectoryPath(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    @Override
    public String toString() {
        return "FileModel{" +
                "id='" + id + '\'' +
                ", fileName='" + fileName + '\'' +
                ", filePath='" + filePath + '\'' +
                ", numberOfRecords=" + numberOfRecords +
                ", directoryPath='" + directoryPath + '\'' +
                ", insertTime=" + insertTime +
                ", lastModified=" + lastModified +
                '}';
    }
}
