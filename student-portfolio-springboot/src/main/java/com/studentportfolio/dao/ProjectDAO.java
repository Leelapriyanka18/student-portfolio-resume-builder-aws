package com.studentportfolio.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.studentportfolio.model.Project;

@Repository
public class ProjectDAO {

    private final JdbcTemplate jdbcTemplate;

    public ProjectDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean saveProject(Project project) {

        String sql = """
                INSERT INTO projects
                (user_id, title, description, github_link)
                VALUES (?, ?, ?, ?)
                """;

        int rows = jdbcTemplate.update(
                sql,
                project.getUserId(),
                project.getTitle(),
                project.getDescription(),
                project.getGithubLink()
        );

        return rows > 0;
    }

    public List<Project> getAllProjects() {

        String sql = """
                SELECT *
                FROM projects
                ORDER BY id DESC
                """;

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> {
                    Project project = new Project();

                    project.setId(rs.getInt("id"));
                    project.setUserId(rs.getInt("user_id"));
                    project.setTitle(rs.getString("title"));
                    project.setDescription(rs.getString("description"));
                    project.setGithubLink(rs.getString("github_link"));

                    return project;
                }
        );
    }
}