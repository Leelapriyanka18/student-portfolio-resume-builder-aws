package com.studentportfolio.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.studentportfolio.model.Contact;

@Repository
public class ContactDAO {

    private final JdbcTemplate jdbcTemplate;

    public ContactDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean saveContact(Contact contact) {

        String sql = """
                INSERT INTO contacts
                (user_id, name, email, subject, message)
                VALUES (?, ?, ?, ?, ?)
                """;

        int rows = jdbcTemplate.update(
                sql,
                contact.getUserId(),
                contact.getName(),
                contact.getEmail(),
                contact.getSubject(),
                contact.getMessage()
        );

        return rows > 0;
    }

    public List<Contact> getAllContacts() {

        String sql = """
                SELECT *
                FROM contacts
                ORDER BY id DESC
                """;

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> {
                    Contact contact = new Contact();

                    contact.setId(rs.getInt("id"));
                    contact.setUserId(rs.getInt("user_id"));
                    contact.setName(rs.getString("name"));
                    contact.setEmail(rs.getString("email"));
                    contact.setSubject(rs.getString("subject"));
                    contact.setMessage(rs.getString("message"));

                    return contact;
                }
        );
    }

    public List<Contact> getContactsByUserId(int userId) {

        String sql = """
                SELECT *
                FROM contacts
                WHERE user_id = ?
                ORDER BY id DESC
                """;

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> {
                    Contact contact = new Contact();

                    contact.setId(rs.getInt("id"));
                    contact.setUserId(rs.getInt("user_id"));
                    contact.setName(rs.getString("name"));
                    contact.setEmail(rs.getString("email"));
                    contact.setSubject(rs.getString("subject"));
                    contact.setMessage(rs.getString("message"));

                    return contact;
                },
                userId
        );
    }
}
