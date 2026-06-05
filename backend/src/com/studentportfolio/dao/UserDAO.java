package com.studentportfolio.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.studentportfolio.model.User;
import com.studentportfolio.util.DBConnection;
import com.studentportfolio.util.PasswordUtil;

public class UserDAO {

    public boolean registerUser(User user) {
        if (user == null || user.getEmail() == null) return false;

        // Prevent duplicate registration
        if (emailExists(user.getEmail())) {
            System.err.println("Registration failed: email already exists");
            return false;
        }

        String sql = "INSERT INTO users(full_name, email, password) VALUES (?, ?, ?)";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, user.getFullName());
            statement.setString(2, user.getEmail());
            // Hash the password before storing
            String hashed = PasswordUtil.hashPassword(user.getPassword());
            statement.setString(3, hashed);

            int rowsInserted = statement.executeUpdate();

            return rowsInserted > 0;

        } catch (SQLException e) {
            System.err.println("Database error in UserDAO.registerUser: " + e.getMessage());
        }

        return false;
    }

    public boolean emailExists(String email) {
        if (email == null) return false;
        String sql = "SELECT 1 FROM users WHERE email = ? LIMIT 1";
        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println("Database error in UserDAO.emailExists: " + e.getMessage());
        }
        return false;
    }

    public User getUserByEmail(String email) {

        String sql = "SELECT * FROM users WHERE email = ?";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, email);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                User user = new User();

                user.setId(resultSet.getInt("id"));
                user.setFullName(resultSet.getString("full_name"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setCreatedAt(resultSet.getTimestamp("created_at"));

                return user;
            }

        } catch (SQLException e) {
            System.err.println("Database error in UserDAO.getUserByEmail: " + e.getMessage());
        }

        return null;
    }

    public User getUserById(int id) {

        String sql = "SELECT * FROM users WHERE id = ?";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                User user = new User();

                user.setId(resultSet.getInt("id"));
                user.setFullName(resultSet.getString("full_name"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setCreatedAt(resultSet.getTimestamp("created_at"));

                return user;
            }

        } catch (SQLException e) {
            System.err.println("Database error in UserDAO.getUserById: " + e.getMessage());
        }

        return null;
    }
}