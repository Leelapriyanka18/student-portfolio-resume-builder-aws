package com.studentportfolio.dao;

import java.util.List;

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
                (user_id, certificate_name, issuer, issue_date, certificate_url)
                VALUES (?, ?, ?, ?, ?)
                """;

        int rows = jdbcTemplate.update(
                sql,
                certificate.getUserId(),
                certificate.getCertificateName(),
                certificate.getIssuer(),
                certificate.getIssueDate(),
                certificate.getCertificateUrl()
        );

        return rows > 0;
    }

    public List<Certificate> getAllCertificates() {

        String sql = """
                SELECT *
                FROM certificates
                ORDER BY id DESC
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> mapCertificate(rs));
    }

    public List<Certificate> getCertificatesByUserId(int userId) {

        String sql = """
                SELECT *
                FROM certificates
                WHERE user_id = ?
                ORDER BY id DESC
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> mapCertificate(rs), userId);
    }

    private Certificate mapCertificate(java.sql.ResultSet rs) throws java.sql.SQLException {
        Certificate certificate = new Certificate();
        certificate.setId(rs.getInt("id"));
        certificate.setUserId(rs.getInt("user_id"));
        certificate.setCertificateName(rs.getString("certificate_name"));
        certificate.setIssuer(rs.getString("issuer"));
        certificate.setIssueDate(rs.getString("issue_date"));
        certificate.setCertificateUrl(rs.getString("certificate_url"));
        certificate.setCreatedAt(rs.getTimestamp("created_at"));
        return certificate;
    }
}
