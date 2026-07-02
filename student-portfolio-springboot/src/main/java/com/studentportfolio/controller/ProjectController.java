package com.studentportfolio.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.studentportfolio.dto.ProjectRequest;
import com.studentportfolio.model.Project;
import com.studentportfolio.security.AuthenticatedUserService;
import com.studentportfolio.service.ProjectService;

import jakarta.validation.Valid;

@RestController
@RequestMapping({"/api/projects", "/api/project"})
public class ProjectController {

    private final ProjectService projectService;
    private final AuthenticatedUserService authenticatedUserService;

    public ProjectController(ProjectService projectService, AuthenticatedUserService authenticatedUserService) {
        this.projectService = projectService;
        this.authenticatedUserService = authenticatedUserService;
    }

    @PostMapping
    public ResponseEntity<String> saveProject(
            @Valid @RequestBody ProjectRequest request) {

        request.setUserId(authenticatedUserService.getAuthenticatedUserId());
        projectService.saveProject(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Project Saved Successfully");
    }

    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects(
            @RequestParam(required = false) Integer userId) {
        return ResponseEntity.ok(projectService.getProjectsByUserId(
                authenticatedUserService.getAuthenticatedUserId()));
    }

    @ExceptionHandler({
            IllegalArgumentException.class,
            IllegalStateException.class
    })
    public ResponseEntity<String> handleBadRequest(Exception ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
