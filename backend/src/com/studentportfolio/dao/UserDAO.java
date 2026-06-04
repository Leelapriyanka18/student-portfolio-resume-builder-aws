package com.studentportfolio.dao;

import com.studentportfolio.model.User;
import com.studentportfolio.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public boolean registerUser(User user) {

        String sql = "INSERT INTO users(full_name, email, password) VALUES (?, ?, ?)";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, user.getFullName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());

            int rowsInserted = statement.executeUpdate();

            return rowsInserted > 0;

        } catch (SQLException e) {
            System.err.println("Database error in UserDAO.registerUser: " + e.getMessage());
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