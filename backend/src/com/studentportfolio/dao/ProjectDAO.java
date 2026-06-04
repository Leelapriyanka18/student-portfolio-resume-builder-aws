package com.studentportfolio.dao;

import com.studentportfolio.model.Project;
import com.studentportfolio.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProjectDAO {

    public boolean addProject(Project project) {

        String sql = "INSERT INTO projects(user_id, title, description, github_link) VALUES (?, ?, ?, ?)";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setInt(1, project.getUserId());
            statement.setString(2, project.getTitle());
            statement.setString(3, project.getDescription());
            statement.setString(4, project.getGithubLink());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Database error in ProjectDAO.addProject: " + e.getMessage());
        }

        return false;
    }

    public List<Project> getProjectsByUserId(int userId) {

        List<Project> projects = new ArrayList<>();

        String sql = "SELECT * FROM projects WHERE user_id = ?";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setInt(1, userId);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {

                Project project = new Project();

                project.setId(rs.getInt("id"));
                project.setUserId(rs.getInt("user_id"));
                project.setTitle(rs.getString("title"));
                project.setDescription(rs.getString("description"));
                project.setGithubLink(rs.getString("github_link"));
                project.setCreatedAt(rs.getTimestamp("created_at"));

                projects.add(project);
            }

        } catch (SQLException e) {
            System.err.println("Database error in ProjectDAO.getProjectsByUserId: " + e.getMessage());
        }

        return projects;
    }
}