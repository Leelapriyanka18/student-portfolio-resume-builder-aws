package com.studentportfolio.model;

import java.sql.Date;

public class Certificate {

    private int id;
    private int userId;
    private String certificateName;
    private String issuer;
    private Date issueDate;

    public Certificate() {
    }

    public Certificate(int id, int userId,
                       String certificateName,
                       String issuer,
                       Date issueDate) {
        this.id = id;
        this.userId = userId;
        this.certificateName = certificateName;
        this.issuer = issuer;
        this.issueDate = issueDate;
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

    public String getCertificateName() {
        return certificateName;
    }

    public void setCertificateName(String certificateName) {
        this.certificateName = certificateName;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }
}