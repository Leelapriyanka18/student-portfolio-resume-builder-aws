package com.studentportfolio.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.studentportfolio.model.Certificate;
import com.studentportfolio.util.DBConnection;

public class CertificateDAO {

    public boolean addCertificate(Certificate certificate) {

        String sql = "INSERT INTO certificates(user_id, certificate_name, issuer, issue_date) VALUES (?, ?, ?, ?)";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setInt(1, certificate.getUserId());
            statement.setString(2, certificate.getCertificateName());
            statement.setString(3, certificate.getIssuer());
            statement.setDate(4, certificate.getIssueDate());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Database error in CertificateDAO.addCertificate: " + e.getMessage());
        }

        return false;
    }

    public List<Certificate> getCertificatesByUserId(int userId) {

        List<Certificate> certificates = new ArrayList<>();

        String sql = "SELECT * FROM certificates WHERE user_id = ?";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setInt(1, userId);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {

                Certificate certificate = new Certificate();

                certificate.setId(rs.getInt("id"));
                certificate.setUserId(rs.getInt("user_id"));
                certificate.setCertificateName(rs.getString("certificate_name"));
                certificate.setIssuer(rs.getString("issuer"));
                certificate.setIssueDate(rs.getDate("issue_date"));

                certificates.add(certificate);
            }

        } catch (SQLException e) {
            System.err.println("Database error in CertificateDAO.getCertificatesByUserId: " + e.getMessage());
        }

        return certificates;
    }
}