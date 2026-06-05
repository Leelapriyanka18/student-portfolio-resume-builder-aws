package com.studentportfolio.service;

import java.util.List;

import com.studentportfolio.dao.CertificateDAO;
import com.studentportfolio.model.Certificate;

public class CertificateService {

    private CertificateDAO certificateDAO;

    public CertificateService() {
        certificateDAO = new CertificateDAO();
    }

    public boolean addCertificate(Certificate certificate) {
        return certificateDAO.addCertificate(certificate);
    }

    public List<Certificate> getCertificatesByUserId(int userId) {
        return certificateDAO.getCertificatesByUserId(userId);
    }
}
