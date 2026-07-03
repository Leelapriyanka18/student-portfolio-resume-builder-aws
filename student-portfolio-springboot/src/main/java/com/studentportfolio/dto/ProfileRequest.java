package com.studentportfolio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ProfileRequest {

    private int userId;

    @NotBlank(message = "Headline is required")
    @Size(max = 255, message = "Headline must be at most 255 characters")
    private String headline;

    private String bio;

    @NotBlank(message = "Phone is required")
    @Size(max = 30, message = "Phone must be at most 30 characters")
    @Pattern(regexp = "^\\+?[0-9()\\-\\s.]{7,30}$", message = "Enter a valid phone number")
    private String phone;

    private String address;

    @Size(max = 500, message = "LinkedIn URL must be at most 500 characters")
    @Pattern(regexp = "^(|(?i:https?)://\\S+)$", message = "Enter a valid LinkedIn URL")
    private String linkedinUrl;

    @Size(max = 500, message = "GitHub URL must be at most 500 characters")
    @Pattern(regexp = "^(|(?i:https?)://\\S+)$", message = "Enter a valid GitHub URL")
    private String githubUrl;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLinkedinUrl() {
        return linkedinUrl;
    }

    public void setLinkedinUrl(String linkedinUrl) {
        this.linkedinUrl = linkedinUrl;
    }

    public String getGithubUrl() {
        return githubUrl;
    }

    public void setGithubUrl(String githubUrl) {
        this.githubUrl = githubUrl;
    }
}
