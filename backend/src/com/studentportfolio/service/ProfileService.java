package com.studentportfolio.service;

import com.studentportfolio.dao.ProfileDAO;
import com.studentportfolio.model.Profile;

public class ProfileService {

    private ProfileDAO profileDAO;

    public ProfileService() {
        profileDAO = new ProfileDAO();
    }

    public boolean saveProfile(Profile profile) {
        return profileDAO.saveProfile(profile);
    }

    public Profile getProfileByUserId(int userId) {
        return profileDAO.getProfileByUserId(userId);
    }
}