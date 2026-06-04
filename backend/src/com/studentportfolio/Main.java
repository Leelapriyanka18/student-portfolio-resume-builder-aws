package com.studentportfolio;

import com.studentportfolio.controller.AuthController;

public class Main {

    public static void main(String[] args) {

        AuthController authController = new AuthController();

        boolean registered = authController.registerUser(
                "Priyanka",
                "test999999@gmail.com",
                "123456"
        );

        if (registered) {
            System.out.println("User Registered Successfully");
        } else {
            System.out.println("User Registration Failed");
        }
    }
}