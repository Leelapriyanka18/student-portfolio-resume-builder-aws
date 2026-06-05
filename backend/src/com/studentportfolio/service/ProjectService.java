package com.studentportfolio.service;

import java.util.List;

import com.studentportfolio.dao.ProjectDAO;
import com.studentportfolio.model.Project;

public class ProjectService {

    private ProjectDAO projectDAO;

    public ProjectService() {
        projectDAO = new ProjectDAO();
    }

    public boolean addProject(Project project) {
        return projectDAO.addProject(project);
    }

    public List<Project> getProjectsByUserId(int userId) {
        return projectDAO.getProjectsByUserId(userId);
    }
}
