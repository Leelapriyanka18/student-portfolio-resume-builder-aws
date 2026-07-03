package com.studentportfolio.dto;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

class AuthenticatedRequestValidationTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void projectRequestDoesNotRequireClientSuppliedUserId() {
        ProjectRequest request = new ProjectRequest();
        request.setTitle("Cloud Portfolio");
        request.setDescription("AWS-hosted portfolio and resume builder");
        request.setGithubLink("https://github.com/student/portfolio");

        Set<ConstraintViolation<ProjectRequest>> violations = validator.validate(request);

        assertTrue(violations.isEmpty());
    }

    @Test
    void profileRequestStillValidatesSubmittedFields() {
        ProfileRequest request = new ProfileRequest();
        request.setHeadline("Student Developer");
        request.setPhone("not-a-phone-number");

        Set<ConstraintViolation<ProfileRequest>> violations = validator.validate(request);

        assertTrue(violations.stream()
                .anyMatch(violation -> "Enter a valid phone number".equals(violation.getMessage())));
    }
}
