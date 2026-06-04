package com.studentportfolio.model;

import java.sql.Timestamp;

public class Project {

    private int id;
    private int userId;
    private String title;
    private String description;
    private String githubLink;
    private Timestamp createdAt;

    public Project() {
    }

    public Project(int id, int userId, String title,
                   String description, String githubLink,
                   Timestamp createdAt) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.githubLink = githubLink;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGithubLink() {
        return githubLink;
    }

    public void setGithubLink(String githubLink) {
        this.githubLink = githubLink;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}