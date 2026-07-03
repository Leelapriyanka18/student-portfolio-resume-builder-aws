package com.studentportfolio.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studentportfolio.dto.LoginRequest;
import com.studentportfolio.dto.LoginResponse;
import com.studentportfolio.dto.RegisterRequest;
import com.studentportfolio.security.JwtUtil;
import com.studentportfolio.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
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
            return ResponseEntity.ok(Map.of(
                    "token", jwtUtil.generateToken(loginResponse.getEmail()),
                    "userId", loginResponse.getUserId(),
                    "fullName", loginResponse.getFullName(),
                    "email", loginResponse.getEmail()
            ));
        }

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("message", "Invalid Email or Password"));
    }

}
