package com.studentportfolio.controller;

import com.studentportfolio.model.User;
import com.studentportfolio.service.UserService;

public class AuthController {

    private final UserService userService;

    public AuthController() {
        userService = new UserService();
    }

    public boolean registerUser(String fullName, String email, String password) {

        User user = new User();

        user.setFullName(fullName);
        user.setEmail(email);
        user.setPassword(password);

        return userService.registerUser(user);
    }

    public User loginUser(String email) {
        return userService.getUserByEmail(email);
    }
}