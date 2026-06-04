package com.studentportfolio.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.studentportfolio.model.Resume;
import com.studentportfolio.util.DBConnection;

public class ResumeDAO {

    public boolean addResume(Resume resume) {

        String sql = "INSERT INTO resumes(user_id, resume_name, file_path) VALUES (?, ?, ?)";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setInt(1, resume.getUserId());
            statement.setString(2, resume.getResumeName());
            statement.setString(3, resume.getFilePath());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Database error in ResumeDAO.addResume: " + e.getMessage());
        }

        return false;
    }

    public List<Resume> getResumesByUserId(int userId) {

        List<Resume> resumes = new ArrayList<>();

        String sql = "SELECT * FROM resumes WHERE user_id = ?";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setInt(1, userId);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {

                Resume resume = new Resume();

                resume.setId(rs.getInt("id"));
                resume.setUserId(rs.getInt("user_id"));
                resume.setResumeName(rs.getString("resume_name"));
                resume.setFilePath(rs.getString("file_path"));
                resume.setCreatedAt(rs.getTimestamp("created_at"));

                resumes.add(resume);
            }

        } catch (SQLException e) {
            System.err.println("Database error in ResumeDAO.getResumesByUserId: " + e.getMessage());
        }

        return resumes;
    }
}