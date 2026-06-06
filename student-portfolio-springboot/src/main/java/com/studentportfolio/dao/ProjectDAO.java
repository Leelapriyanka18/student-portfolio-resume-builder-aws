package com.studentportfolio.dao;

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
}