package com.studentportfolio.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.studentportfolio.model.Certificate;

@Repository
public class CertificateDAO {

    private final JdbcTemplate jdbcTemplate;

    public CertificateDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean saveCertificate(Certificate certificate) {

        String sql = """
                INSERT INTO certificates
                (user_id, certificate_name, issuer, issue_date)
                VALUES (?, ?, ?, ?)
                """;

        int rows = jdbcTemplate.update(
                sql,
                certificate.getUserId(),
                certificate.getCertificateName(),
                certificate.getIssuer(),
                certificate.getIssueDate()
        );

        return rows > 0;
    }
}