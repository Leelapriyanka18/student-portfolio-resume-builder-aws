package com.studentportfolio.dao;

import com.studentportfolio.model.User;
import com.studentportfolio.util.PasswordUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public class UserDAO {

    private final JdbcTemplate jdbcTemplate;

    public UserDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean registerUser(User user) {
        if (user == null || user.getEmail() == null) {
            return false;
        }

        if (emailExists(user.getEmail())) {
            return false;
        }

        String sql = "INSERT INTO users(full_name, email, password) VALUES (?, ?, ?)";
        String hashedPassword = PasswordUtil.hashPassword(user.getPassword());

        int rows = jdbcTemplate.update(sql,
                user.getFullName(),
                user.getEmail(),
                hashedPassword);

        return rows > 0;
    }

    public boolean emailExists(String email) {
        if (email == null) {
            return false;
        }

        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(1) FROM users WHERE email = ?",
                Integer.class,
                email
        );

        return count != null && count > 0;
    }

    public User getUserByEmail(String email) {
        String sql = "SELECT id, full_name, email, password, created_at FROM users WHERE email = ?";

        return jdbcTemplate.queryForObject(sql, userRowMapper(), email);
    }

    public User getUserById(int id) {
        String sql = "SELECT id, full_name, email, password, created_at FROM users WHERE id = ?";

        return jdbcTemplate.queryForObject(sql, userRowMapper(), id);
    }

    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setFullName(rs.getString("full_name"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setCreatedAt(rs.getTimestamp("created_at"));
            return user;
        };
    }
}
