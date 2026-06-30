package com.studentportfolio.controller;

import com.studentportfolio.model.User;
import com.studentportfolio.service.UserService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@CrossOrigin(origins = "http://student-portfolio-priyanka-4501.s3-website-us-east-1.amazonaws.com")
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController() {
        userService = new UserService();
    }

    public boolean registerUser(String fullName, String email, String password) {
        if (email == null) return false;

        if (userService.emailExists(email)) {
            System.out.println("User with this email already exists: " + email);
            return false;
        }

        User user = new User();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPassword(password);

        boolean registered = userService.registerUser(user);
        if (registered) {
            System.out.println("User Registered Successfully");
        }
        return registered;
    }

    public User loginUser(String email, String password) {
        if (email == null || password == null) {
            System.out.println("Login failed: email and password are required.");
            return null;
        }

        User user = userService.authenticate(email, password);
        if (user == null) {
            System.out.println("Login failed: invalid credentials.");
        }
        return user;
    }
}