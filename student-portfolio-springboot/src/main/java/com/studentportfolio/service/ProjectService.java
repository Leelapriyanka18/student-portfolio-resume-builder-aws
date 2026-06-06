package com.studentportfolio.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.studentportfolio.dao.ProjectDAO;
import com.studentportfolio.dto.ProjectRequest;
import com.studentportfolio.model.Project;

@Service
public class ProjectService {

    private final ProjectDAO projectDAO;

    public ProjectService(ProjectDAO projectDAO) {
        this.projectDAO = projectDAO;
    }

    @Transactional
    public void saveProject(ProjectRequest request) {

        Project project = new Project();

        // Temporary user id
        project.setUserId(1);

        project.setTitle(request.getTitle());
        project.setDescription(request.getDescription());
        project.setGithubLink(request.getGithubLink());

        boolean saved = projectDAO.saveProject(project);

        if (!saved) {
            throw new IllegalStateException("Unable to save project");
        }
    }
}