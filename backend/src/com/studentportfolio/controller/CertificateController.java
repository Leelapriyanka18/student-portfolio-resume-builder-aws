package com.studentportfolio.controller;

import java.sql.Date;
import java.util.List;

import com.studentportfolio.model.Certificate;
import com.studentportfolio.service.CertificateService;

public class CertificateController {

    private CertificateService certificateService;

    public CertificateController() {
        certificateService = new CertificateService();
    }

    public boolean addCertificate(int userId,
                                  String certificateName,
                                  String issuer,
                                  Date issueDate) {

        Certificate certificate = new Certificate();

        certificate.setUserId(userId);
        certificate.setCertificateName(certificateName);
        certificate.setIssuer(issuer);
        certificate.setIssueDate(issueDate);

        return certificateService.addCertificate(certificate);
    }

    public List<Certificate> getCertificates(int userId) {
        return certificateService.getCertificatesByUserId(userId);
    }
}