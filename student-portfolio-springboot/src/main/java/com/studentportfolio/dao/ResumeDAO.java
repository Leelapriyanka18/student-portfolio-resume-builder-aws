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
                experience,
                github,
                linkedin,
                languages,
                hobbies,
                file_path
                )
                VALUES (?,?,?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
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
                resume.getExperience(),
                resume.getGithub(),
                resume.getLinkedin(),
                resume.getLanguages(),
                resume.getHobbies(),
                resume.getFilePath());

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

resume.setEmail(rs.getString("email"));
resume.setPhone(rs.getString("phone"));
resume.setAddress(rs.getString("address"));
resume.setRole(rs.getString("role"));
resume.setSummary(rs.getString("summary"));

resume.setCollege(rs.getString("college"));
resume.setDegree(rs.getString("degree"));
resume.setBranch(rs.getString("branch"));
resume.setGraduationYear(rs.getString("graduation_year"));
resume.setCgpa(rs.getString("cgpa"));

resume.setSkills(rs.getString("skills"));
resume.setProjects(rs.getString("projects"));
resume.setProjectDescription(rs.getString("project_description"));

resume.setCertificates(rs.getString("certificates"));
resume.setCertificateDetails(rs.getString("certificate_details"));

resume.setExperience(rs.getString("experience"));
resume.setGithub(rs.getString("github"));
resume.setLinkedin(rs.getString("linkedin"));

resume.setLanguages(rs.getString("languages"));
resume.setHobbies(rs.getString("hobbies"));

resume.setFilePath(rs.getString("file_path"));
resume.setCreatedAt(rs.getTimestamp("created_at"));
                    return resume;
                });
    }
}