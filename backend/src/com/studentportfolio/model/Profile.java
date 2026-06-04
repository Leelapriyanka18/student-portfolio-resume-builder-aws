package com.studentportfolio.model;

public class Profile {

    private int id;
    private int userId;
    private String headline;
    private String bio;
    private String phone;
    private String address;
    private String linkedinUrl;
    private String githubUrl;

    public Profile() {
    }

    public Profile(int id, int userId, String headline,
                   String bio, String phone, String address,
                   String linkedinUrl, String githubUrl) {
        this.id = id;
        this.userId = userId;
        this.headline = headline;
        this.bio = bio;
        this.phone = phone;
        this.address = address;
        this.linkedinUrl = linkedinUrl;
        this.githubUrl = githubUrl;
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

    @Override
    public String toString() {
        return "Profile{" +
                "id=" + id +
                ", userId=" + userId +
                ", headline='" + headline + '\'' +
                ", bio='" + bio + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", linkedinUrl='" + linkedinUrl + '\'' +
                ", githubUrl='" + githubUrl + '\'' +
                '}';
    }
}