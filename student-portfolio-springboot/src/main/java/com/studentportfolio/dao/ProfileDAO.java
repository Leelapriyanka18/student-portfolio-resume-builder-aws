package com.studentportfolio.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.studentportfolio.model.Profile;

@Repository
public class ProfileDAO {

    private final JdbcTemplate jdbcTemplate;

    public ProfileDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean saveProfile(Profile profile) {

        String sql = """
                INSERT INTO profiles
                (user_id, headline, bio, phone, address, linkedin_url, github_url)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        int rows = jdbcTemplate.update(
                sql,
                profile.getUserId(),
                profile.getHeadline(),
                profile.getBio(),
                profile.getPhone(),
                profile.getAddress(),
                profile.getLinkedinUrl(),
                profile.getGithubUrl()
        );

        return rows > 0;
    }
}