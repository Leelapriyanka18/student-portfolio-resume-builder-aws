package com.studentportfolio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ProjectRequest {

    private int userId;

    @NotBlank(message = "Project title is required")
    @Size(max = 200, message = "Project title must be at most 200 characters")
    private String title;

    @NotBlank(message = "Project description is required")
    private String description;

    @Size(max = 500, message = "GitHub link must be at most 500 characters")
    @Pattern(regexp = "^(|(?i:https?)://\\S+)$", message = "Enter a valid GitHub URL")
    private String githubLink;

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
}
