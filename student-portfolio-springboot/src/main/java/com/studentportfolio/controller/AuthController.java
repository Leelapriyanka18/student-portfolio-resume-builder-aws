package com.studentportfolio.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studentportfolio.dto.LoginRequest;
import com.studentportfolio.dto.LoginResponse;
import com.studentportfolio.dto.RegisterRequest;
import com.studentportfolio.service.UserService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = {
    "http://student-portfolio-priyanka-4501.s3-website-us-east-1.amazonaws.com"
})
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @Valid @RequestBody RegisterRequest request) {

        userService.register(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Registration Successful");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Valid @RequestBody LoginRequest request) {
        LoginResponse loginResponse = userService.login(request);

        if (loginResponse != null) {
            return ResponseEntity.ok(loginResponse);
        }

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("message", "Invalid Email or Password"));
    }

    @ExceptionHandler({
            IllegalArgumentException.class,
            IllegalStateException.class
    })
    public ResponseEntity<String> handleException(Exception ex) {

        return ResponseEntity
                .badRequest()
                .body(ex.getMessage());
    }
}
