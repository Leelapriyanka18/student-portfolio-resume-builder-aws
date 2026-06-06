package com.studentportfolio.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.studentportfolio.dao.ProfileDAO;
import com.studentportfolio.dto.ProfileRequest;
import com.studentportfolio.model.Profile;

@Service
public class ProfileService {

    private final ProfileDAO profileDAO;

    public ProfileService(ProfileDAO profileDAO) {
        this.profileDAO = profileDAO;
    }

    @Transactional
    public void saveProfile(int userId, ProfileRequest request) {

        Profile profile = new Profile();

        profile.setUserId(userId);
        profile.setHeadline(request.getHeadline());
        profile.setBio(request.getBio());
        profile.setPhone(request.getPhone());
        profile.setAddress(request.getAddress());
        profile.setLinkedinUrl(request.getLinkedinUrl());
        profile.setGithubUrl(request.getGithubUrl());

        boolean saved = profileDAO.saveProfile(profile);

        if (!saved) {
            throw new IllegalStateException("Unable to save profile");
        }
    }
}