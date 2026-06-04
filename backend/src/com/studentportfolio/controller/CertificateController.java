package com.studentportfolio.controller;

import com.studentportfolio.dao.CertificateDAO;
import com.studentportfolio.model.Certificate;

import java.sql.Date;
import java.util.List;

public class CertificateController {

    private CertificateDAO certificateDAO;

    public CertificateController() {
        certificateDAO = new CertificateDAO();
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

        return certificateDAO.addCertificate(certificate);
    }

    public List<Certificate> getCertificates(int userId) {
        return certificateDAO.getCertificatesByUserId(userId);
    }
}