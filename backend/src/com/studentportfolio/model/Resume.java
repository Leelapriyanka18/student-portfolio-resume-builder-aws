package com.studentportfolio.model;

import java.sql.Timestamp;

public class Resume {

    private int id;
    private int userId;
    private String resumeName;
    private String filePath;
    private Timestamp createdAt;

    public Resume() {
    }

    public Resume(int id, int userId, String resumeName,
                  String filePath, Timestamp createdAt) {
        this.id = id;
        this.userId = userId;
        this.resumeName = resumeName;
        this.filePath = filePath;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getResumeName() {
        return resumeName;
    }

    public void setResumeName(String resumeName) {
        this.resumeName = resumeName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}