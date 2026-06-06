package com.studentportfolio.dto;

import jakarta.validation.constraints.NotBlank;

public class CertificateRequest {

    @NotBlank(message = "Certificate name is required")
    private String certificateName;

    @NotBlank(message = "Issuer is required")
    private String issuer;

    @NotBlank(message = "Issue date is required")
    private String issueDate;

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

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }
}