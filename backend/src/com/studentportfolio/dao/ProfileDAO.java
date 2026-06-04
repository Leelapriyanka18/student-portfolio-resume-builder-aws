package com.studentportfolio.dao;

import com.studentportfolio.model.Profile;
import com.studentportfolio.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfileDAO {

    public boolean saveProfile(Profile profile) {

        String sql = "INSERT INTO profiles(user_id, headline, bio, phone, address, linkedin_url, github_url) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setInt(1, profile.getUserId());
            statement.setString(2, profile.getHeadline());
            statement.setString(3, profile.getBio());
            statement.setString(4, profile.getPhone());
            statement.setString(5, profile.getAddress());
            statement.setString(6, profile.getLinkedinUrl());
            statement.setString(7, profile.getGithubUrl());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Database error in ProfileDAO.saveProfile: " + e.getMessage());
        }

        return false;
    }

    public Profile getProfileByUserId(int userId) {

        String sql = "SELECT * FROM profiles WHERE user_id = ?";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setInt(1, userId);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {

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
            }

        } catch (SQLException e) {
            System.err.println("Database error in ProfileDAO.getProfileByUserId: " + e.getMessage());
        }

        return null;
    }
}