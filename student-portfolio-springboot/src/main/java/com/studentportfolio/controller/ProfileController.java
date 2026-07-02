package com.studentportfolio.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.studentportfolio.dto.ProfileRequest;
import com.studentportfolio.model.Profile;
import com.studentportfolio.security.AuthenticatedUserService;
import com.studentportfolio.service.ProfileService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private final ProfileService profileService;
    private final AuthenticatedUserService authenticatedUserService;

    public ProfileController(ProfileService profileService, AuthenticatedUserService authenticatedUserService) {
        this.profileService = profileService;
        this.authenticatedUserService = authenticatedUserService;
    }

    @PostMapping
    public ResponseEntity<String> saveProfile(
            @Valid @RequestBody ProfileRequest request) {

        request.setUserId(authenticatedUserService.getAuthenticatedUserId());
        profileService.saveProfile(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Profile Saved Successfully");
    }

    @GetMapping
    public ResponseEntity<Profile> getProfile(@RequestParam int userId) {
        Profile profile = profileService.getProfileByUserId(authenticatedUserService.getAuthenticatedUserId());

        if (profile == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(profile);
    }

    @ExceptionHandler({IllegalArgumentException.class,
            IllegalStateException.class})
    public ResponseEntity<String> handleBadRequest(Exception ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
