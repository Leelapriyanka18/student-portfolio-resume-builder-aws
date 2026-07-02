package com.studentportfolio.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import com.studentportfolio.model.Profile;

@Repository
public class ProfileDAO {

    private final JdbcTemplate jdbcTemplate;

    public ProfileDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean saveProfile(Profile profile) {
        Integer existingProfileId = findProfileIdByUserId(profile.getUserId());

        if (existingProfileId == null) {
            return insertProfile(profile);
        }

        return updateProfile(existingProfileId, profile);
    }

    private boolean insertProfile(Profile profile) {
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

    private boolean updateProfile(int profileId, Profile profile) {
        String sql = """
                UPDATE profiles
                SET headline = ?,
                    bio = ?,
                    phone = ?,
                    address = ?,
                    linkedin_url = ?,
                    github_url = ?
                WHERE id = ? AND user_id = ?
                """;

        int rows = jdbcTemplate.update(
                sql,
                profile.getHeadline(),
                profile.getBio(),
                profile.getPhone(),
                profile.getAddress(),
                profile.getLinkedinUrl(),
                profile.getGithubUrl(),
                profileId,
                profile.getUserId()
        );

        return rows > 0;
    }

    private Integer findProfileIdByUserId(int userId) {
        String sql = """
                SELECT id
                FROM profiles
                WHERE user_id = ?
                ORDER BY id DESC
                LIMIT 1
                """;

        try {
            return jdbcTemplate.queryForObject(sql, Integer.class, userId);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public Profile getProfileByUserId(int userId) {
        String sql = """
                SELECT *
                FROM profiles
                WHERE user_id = ?
                ORDER BY id DESC
                LIMIT 1
                """;

        try {
            return jdbcTemplate.queryForObject(
                    sql,
                    (rs, rowNum) -> {
                        Profile profile = new Profile();
                        profile.setId(rs.getInt("id"));
                        profile.setUserId(rs.getInt("user_id"));
                        profile.setHeadline(rs.getString("headline"));
                        profile.setBio(rs.getString("bio"));
                        profile.setPhone(rs.getString("phone"));
                        profile.setAddress(rs.getString("address"));
                        profile.setLinkedinUrl(rs.getString("linkedin_url"));
                        profile.setGithubUrl(rs.getString("github_url"));
                        return profile;
                    },
                    userId
            );
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }
}
