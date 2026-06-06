package com.studentportfolio.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.studentportfolio.model.Resume;

@Repository
public class ResumeDAO {

    private final JdbcTemplate jdbcTemplate;

    public ResumeDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean saveResume(Resume resume) {

        String sql = """
                INSERT INTO resumes
                (user_id, resume_name, file_path)
                VALUES (?, ?, ?)
                """;

        int rows = jdbcTemplate.update(
                sql,
                resume.getUserId(),
                resume.getResumeName(),
                resume.getFilePath()
        );

        return rows > 0;
    }
}