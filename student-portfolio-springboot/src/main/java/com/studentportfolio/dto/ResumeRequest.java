package com.studentportfolio.dto;

import jakarta.validation.constraints.NotBlank;

public class ResumeRequest {

    @NotBlank(message = "Resume name is required")
    private String resumeName;

    private String filePath;

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
}