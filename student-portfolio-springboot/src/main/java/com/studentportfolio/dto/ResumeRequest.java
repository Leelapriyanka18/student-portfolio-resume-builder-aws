package com.studentportfolio.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ResumeRequest {
    private int userId;

    @Email(message = "Enter a valid email")
    @Size(max = 150, message = "Email must be at most 150 characters")
    private String email;

    @Size(max = 30, message = "Phone must be at most 30 characters")
    @Pattern(regexp = "^(|\\+?[0-9()\\-\\s.]{7,30})$", message = "Enter a valid phone number")
    private String phone;
    private String address;

    @Size(max = 150, message = "Role must be at most 150 characters")
    private String role;
    private String summary;

    @Size(max = 200, message = "College must be at most 200 characters")
    private String college;

    @Size(max = 150, message = "Degree must be at most 150 characters")
    private String degree;

    @Size(max = 150, message = "Branch must be at most 150 characters")
    private String branch;

    @Size(max = 20, message = "Graduation year must be at most 20 characters")
    private String graduationYear;

    @Size(max = 20, message = "CGPA must be at most 20 characters")
    private String cgpa;
    private String skills;
    private String projects;
    private String projectDescription;
    private String certificates;
    private String certificateDetails;

    @Size(max = 255, message = "Languages must be at most 255 characters")
    private String languages;

    @Size(max = 255, message = "Hobbies must be at most 255 characters")
    private String hobbies;
    private String experience;

    @Size(max = 500, message = "GitHub URL must be at most 500 characters")
    @Pattern(regexp = "^(|(?i:https?)://\\S+)$", message = "Enter a valid GitHub URL")
    private String github;

    @Size(max = 500, message = "LinkedIn URL must be at most 500 characters")
    @Pattern(regexp = "^(|(?i:https?)://\\S+)$", message = "Enter a valid LinkedIn URL")
    private String linkedin;

    @NotBlank(message = "Resume name is required")
    @Size(max = 200, message = "Resume name must be at most 200 characters")
    private String resumeName;

    @Size(max = 500, message = "File path must be at most 500 characters")
    private String filePath;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getGraduationYear() {
        return graduationYear;
    }

    public void setGraduationYear(String graduationYear) {
        this.graduationYear = graduationYear;
    }

    public String getCgpa() {
        return cgpa;
    }

    public void setCgpa(String cgpa) {
        this.cgpa = cgpa;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getProjects() {
        return projects;
    }

    public void setProjects(String projects) {
        this.projects = projects;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public String getCertificates() {
        return certificates;
    }

    public void setCertificates(String certificates) {
        this.certificates = certificates;
    }

    public String getCertificateDetails() {
        return certificateDetails;
    }

    public void setCertificateDetails(String certificateDetails) {
        this.certificateDetails = certificateDetails;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getGithub() {
        return github;
    }

    public void setGithub(String github) {
        this.github = github;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

}
