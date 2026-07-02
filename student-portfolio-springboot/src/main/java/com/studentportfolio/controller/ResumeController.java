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

import com.studentportfolio.dto.ResumeRequest;
import com.studentportfolio.model.Resume;
import com.studentportfolio.security.AuthenticatedUserService;
import com.studentportfolio.service.ResumeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/resume")
public class ResumeController {

    private final ResumeService resumeService;
    private final AuthenticatedUserService authenticatedUserService;

    public ResumeController(ResumeService resumeService, AuthenticatedUserService authenticatedUserService) {
        this.resumeService = resumeService;
        this.authenticatedUserService = authenticatedUserService;
    }

    @PostMapping
    public ResponseEntity<String> saveResume(
            @Valid @RequestBody ResumeRequest request) {

        request.setUserId(authenticatedUserService.getAuthenticatedUserId());
        resumeService.saveResume(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Resume Saved Successfully");
    }

    @GetMapping
    public ResponseEntity<List<Resume>> getAllResumes(
            @RequestParam(required = false) Integer userId) {
        return ResponseEntity.ok(resumeService.getResumesByUserId(
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
