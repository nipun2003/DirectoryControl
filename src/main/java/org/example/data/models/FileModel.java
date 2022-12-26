package org.example.data.models;

import org.example.core.FileHelper;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class FileModel {

    private String id;
    private String fileName = "";
    private String fileData = "";
    private String filePath = "";
    private String directoryPath = "";
    private int numberOfRecords = 0;
    private Timestamp insertTime;
    private Timestamp lastModified;
    private String status = "READ";

    public FileModel() {
    }

    public void readFile(){
        FileHelper fileHelper = new FileHelper(filePath);
        List<String> data = fileHelper.getAllDataSeperatedWithSemicolon();
        if (!data.isEmpty()) {
            this.fileData = String.join(";",data);
            this.numberOfRecords = data.size();
        }
    }

    public boolean copyFile(String destination){
        boolean isCopied = false;
        try {
            FileHelper fileHelper = new FileHelper(filePath);
            fileHelper.copyFile(destination,directoryPath);
        } catch (IOException e){
            e.printStackTrace();
        }
        return isCopied;
    }

    public FileModel(File file,String parent) {
        this.fileName = file.getName();
        this.filePath = file.getPath();
        this.directoryPath = parent;
        this.lastModified = new Timestamp(file.lastModified());
        this.insertTime = new Timestamp(new Date().getTime());
        this.id = filePath + lastModified;
        this.status = "READ";
    }

    public FileModel(String fileName, String fileData, String filePath, String directoryPath, int numberOfRecords, Timestamp lastModified) {
        this.fileName = fileName;
        this.fileData = fileData;
        this.filePath = filePath;
        this.directoryPath = directoryPath;
        this.numberOfRecords = numberOfRecords;
        this.lastModified = lastModified;
        this.id = filePath + lastModified;
        this.insertTime = new Timestamp(new Date().getTime());
        this.status = "READ";
    }

    public FileModel(String id, String fileName, String fileData, String filePath, String directoryPath, int numberOfRecords, Timestamp insertTime, Timestamp lastModified, String status) {
        this.id = id;
        this.fileName = fileName;
        this.fileData = fileData;
        this.filePath = filePath;
        this.directoryPath = directoryPath;
        this.numberOfRecords = numberOfRecords;
        this.insertTime = insertTime;
        this.lastModified = lastModified;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileData() {
        return fileData;
    }

    public void setFileData(String fileData) {
        this.fileData = fileData;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getDirectoryPath() {
        return directoryPath;
    }

    public void setDirectoryPath(String directoryPath) {
        this.directoryPath = directoryPath;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "FileModel{" +
                "id='" + id + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileData='" + fileData + '\'' +
                ", filePath='" + filePath + '\'' +
                ", directoryPath='" + directoryPath + '\'' +
                ", numberOfRecords=" + numberOfRecords +
                ", insertTime=" + insertTime +
                ", lastModified=" + lastModified +
                ", status='" + status + '\'' +
                '}';
    }
}
