package com.studentportfolio.controller;

import java.util.List;

import com.studentportfolio.model.Project;
import com.studentportfolio.service.ProjectService;

public class ProjectController {

    private ProjectService projectService;

    public ProjectController() {
        projectService = new ProjectService();
    }

    public boolean addProject(int userId,
                              String title,
                              String description,
                              String githubLink) {

        Project project = new Project();

        project.setUserId(userId);
        project.setTitle(title);
        project.setDescription(description);
        project.setGithubLink(githubLink);

        return projectService.addProject(project);
    }

    public List<Project> getProjects(int userId) {
        return projectService.getProjectsByUserId(userId);
    }
}
