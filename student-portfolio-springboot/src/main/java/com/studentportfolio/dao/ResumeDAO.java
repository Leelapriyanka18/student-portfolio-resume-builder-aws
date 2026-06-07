package com.studentportfolio.dao;

import java.util.List;

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

    public List<Resume> getAllResumes() {

        String sql = """
                SELECT *
                FROM resumes
                ORDER BY id DESC
                """;

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> {
                    Resume resume = new Resume();

                    resume.setId(rs.getInt("id"));
                    resume.setUserId(rs.getInt("user_id"));
                    resume.setResumeName(rs.getString("resume_name"));
                    resume.setFilePath(rs.getString("file_path"));
                    resume.setCreatedAt(rs.getTimestamp("created_at"));

                    return resume;
                }
        );
    }
}