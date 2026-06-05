package com.studentportfolio;

import com.studentportfolio.controller.AuthController;
import com.studentportfolio.controller.ProfileController;
import com.studentportfolio.model.Profile;
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

        System.out.println("Registration Result: " +
                (registered ? "SUCCESS" : "FAILED"));

        System.out.println("\n=== Successful Login Test ===");

        User user = authController.loginUser(
                TEST_EMAIL,
                TEST_PASSWORD
        );

        if (user != null) {
            System.out.println("Login Success");
            System.out.println("Name: " + user.getFullName());
            System.out.println("Email: " + user.getEmail());

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

        } else {
            System.out.println("Login Failed");
        }

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

        System.out.println("\n=== Duplicate Registration Test ===");

        boolean duplicate = authController.registerUser(
                TEST_NAME,
                TEST_EMAIL,
                TEST_PASSWORD
        );

        System.out.println(duplicate
                        ? "ERROR: Duplicate Registration Allowed"
                        : "Duplicate Registration Blocked Successfully"
        );
    }
}