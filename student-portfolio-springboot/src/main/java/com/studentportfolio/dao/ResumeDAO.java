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
        (
        user_id,
        resume_name,
        email,
        phone,
        address,
        role,
        summary,
        college,
        degree,
        branch,
        graduation_year,
        cgpa,
        skills,
        projects,
        project_description,
        certificates,
        certificate_details,
        languages,
        hobbies,
        file_path
        )
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        int rows = jdbcTemplate.update(
        sql,
        resume.getUserId(),
        resume.getResumeName(),
        resume.getEmail(),
        resume.getPhone(),
        resume.getAddress(),
        resume.getRole(),
        resume.getSummary(),
        resume.getCollege(),
        resume.getDegree(),
        resume.getBranch(),
        resume.getGraduationYear(),
        resume.getCgpa(),
        resume.getSkills(),
        resume.getProjects(),
        resume.getProjectDescription(),
        resume.getCertificates(),
        resume.getCertificateDetails(),
        resume.getLanguages(),
        resume.getHobbies(),
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