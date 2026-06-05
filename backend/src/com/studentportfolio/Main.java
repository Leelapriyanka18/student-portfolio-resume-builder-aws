package com.studentportfolio;

import java.util.List;

import com.studentportfolio.controller.AuthController;
import com.studentportfolio.controller.ProfileController;
import com.studentportfolio.controller.ProjectController;
import com.studentportfolio.controller.ResumeController;
import com.studentportfolio.model.Profile;
import com.studentportfolio.model.Project;
import com.studentportfolio.model.Resume;
import com.studentportfolio.model.User;

public class Main {

    private static final String TEST_EMAIL = "test996666666@gmail.com";
    private static final String TEST_NAME = "Priyanka";
    private static final String TEST_PASSWORD = "Password123!";
    private static final String WRONG_PASSWORD = "WrongPass123";

    public static void main(String[] args) {

        AuthController authController = new AuthController();

        System.out.println("=== Registration Test ===");

        boolean registered = authController.registerUser(
                TEST_NAME,
                TEST_EMAIL,
                TEST_PASSWORD
        );

        System.out.println("Registration Result: "
                + (registered ? "SUCCESS" : "FAILED"));

        System.out.println("\n=== Successful Login Test ===");

        User user = authController.loginUser(
                TEST_EMAIL,
                TEST_PASSWORD
        );

        if (user != null) {

            System.out.println("Login Success");
            System.out.println("Name: " + user.getFullName());
            System.out.println("Email: " + user.getEmail());

            // =========================
            // PROFILE MODULE TEST
            // =========================

            System.out.println("\n=== Profile Save Test ===");

            ProfileController profileController = new ProfileController();

            boolean profileSaved = profileController.saveProfile(
                    user.getId(),
                    "Senior Software Engineer",
                    "Experienced full-stack developer building scalable Java and AWS applications.",
                    "+1-555-123-4567",
                    "456 Innovation Drive, Tech City, USA",
                    "https://linkedin.com/in/priyanka",
                    "https://github.com/priyanka"
            );

            System.out.println(
                    profileSaved
                            ? "Profile Save Success"
                            : "Profile Save Failed"
            );

            System.out.println("\n=== Profile Read Test ===");

            Profile profile = profileController.getProfile(user.getId());

            if (profile != null) {

                System.out.println("Profile Read Success");
                System.out.println("Headline: " + profile.getHeadline());
                System.out.println("Bio: " + profile.getBio());
                System.out.println("Phone: " + profile.getPhone());
                System.out.println("Address: " + profile.getAddress());
                System.out.println("LinkedIn URL: " + profile.getLinkedinUrl());
                System.out.println("GitHub URL: " + profile.getGithubUrl());

            } else {

                System.out.println("Profile Read Failed");
            }

            // =========================
            // RESUME MODULE TEST
            // =========================

            System.out.println("\n=== Resume Upload Test ===");

            ResumeController resumeController = new ResumeController();

            boolean resumeUploaded = resumeController.uploadResume(
                    user.getId(),
                    "Priyanka_Resume.pdf",
                    "/resumes/Priyanka_Resume.pdf"
            );

            System.out.println(
                    resumeUploaded
                            ? "Resume Upload Success"
                            : "Resume Upload Failed"
            );

            System.out.println("\n=== Resume Read Test ===");

            List<Resume> resumes =
                    resumeController.getResumes(user.getId());

            if (resumes != null && !resumes.isEmpty()) {

                System.out.println("Resume Read Success");

                for (Resume resume : resumes) {

                    System.out.println("Resume ID: " + resume.getId());
                    System.out.println("Resume Name: " + resume.getResumeName());
                    System.out.println("File Path: " + resume.getFilePath());
                    System.out.println("Created At: " + resume.getCreatedAt());
                    System.out.println("---");
                }

            } else {

                System.out.println("Resume Read Failed");
            }

            // =========================
            // PROJECT MODULE TEST
            // =========================

            System.out.println("\n=== Project Save Test ===");

            ProjectController projectController = new ProjectController();

            boolean projectSaved = projectController.addProject(
                    user.getId(),
                    "Student Portfolio Builder",
                    "Cloud-based student portfolio and resume builder application.",
                    "https://github.com/Leelapriyanka18/student-portfolio-resume-builder-aws"
            );

            System.out.println(
                    projectSaved
                            ? "Project Save Success"
                            : "Project Save Failed"
            );

            System.out.println("\n=== Project Read Test ===");

            List<Project> projects =
                    projectController.getProjects(user.getId());

            if (projects != null && !projects.isEmpty()) {

                System.out.println("Project Read Success");

                for (Project project : projects) {

                    System.out.println("Project ID: " + project.getId());
                    System.out.println("Title: " + project.getTitle());
                    System.out.println("Description: " + project.getDescription());
                    System.out.println("GitHub Link: " + project.getGithubLink());
                    System.out.println("Created At: " + project.getCreatedAt());
                    System.out.println("---");
                }

            } else {

                System.out.println("Project Read Failed");
            }

        } else {

            System.out.println("Login Failed");
        }

        // =========================
        // WRONG PASSWORD TEST
        // =========================

        System.out.println("\n=== Wrong Password Test ===");

        User wrongUser = authController.loginUser(
                TEST_EMAIL,
                WRONG_PASSWORD
        );

        if (wrongUser == null) {

            System.out.println("Wrong Password Rejected Successfully");

        } else {

            System.out.println("ERROR: Wrong Password Accepted");
        }

        // =========================
        // DUPLICATE REGISTRATION TEST
        // =========================

        System.out.println("\n=== Duplicate Registration Test ===");

        boolean duplicate = authController.registerUser(
                TEST_NAME,
                TEST_EMAIL,
                TEST_PASSWORD
        );

        System.out.println(
                duplicate
                        ? "ERROR: Duplicate Registration Allowed"
                        : "Duplicate Registration Blocked Successfully"
        );
    }
}