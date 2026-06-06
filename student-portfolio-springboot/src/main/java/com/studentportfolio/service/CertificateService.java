package com.studentportfolio.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.studentportfolio.dao.CertificateDAO;
import com.studentportfolio.dto.CertificateRequest;
import com.studentportfolio.model.Certificate;

@Service
public class CertificateService {

    private final CertificateDAO certificateDAO;

    public CertificateService(CertificateDAO certificateDAO) {
        this.certificateDAO = certificateDAO;
    }

    @Transactional
    public void saveCertificate(CertificateRequest request) {

        Certificate certificate = new Certificate();

        // Temporary user id
        certificate.setUserId(1);

        certificate.setCertificateName(request.getCertificateName());
        certificate.setIssuer(request.getIssuer());
        certificate.setIssueDate(request.getIssueDate());

        boolean saved = certificateDAO.saveCertificate(certificate);

        if (!saved) {
            throw new IllegalStateException("Unable to save certificate");
        }
    }
}