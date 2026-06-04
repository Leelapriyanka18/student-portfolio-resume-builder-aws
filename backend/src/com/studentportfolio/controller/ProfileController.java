package com.studentportfolio.controller;

import com.studentportfolio.model.Profile;
import com.studentportfolio.service.ProfileService;

public class ProfileController {

    private ProfileService profileService;

    public ProfileController() {
        profileService = new ProfileService();
    }

    public boolean saveProfile(int userId,
                               String headline,
                               String bio,
                               String phone,
                               String address,
                               String linkedinUrl,
                               String githubUrl) {

        Profile profile = new Profile();

        profile.setUserId(userId);
        profile.setHeadline(headline);
        profile.setBio(bio);
        profile.setPhone(phone);
        profile.setAddress(address);
        profile.setLinkedinUrl(linkedinUrl);
        profile.setGithubUrl(githubUrl);

        return profileService.saveProfile(profile);
    }

    public Profile getProfile(int userId) {
        return profileService.getProfileByUserId(userId);
    }
}