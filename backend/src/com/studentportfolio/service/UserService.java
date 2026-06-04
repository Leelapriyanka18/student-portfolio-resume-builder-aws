package com.studentportfolio.service;

import com.studentportfolio.dao.UserDAO;
import com.studentportfolio.model.User;

public class UserService {

    private UserDAO userDAO;

    public UserService() {
        userDAO = new UserDAO();
    }

    public boolean registerUser(User user) {
        return userDAO.registerUser(user);
    }

    public User getUserByEmail(String email) {
        return userDAO.getUserByEmail(email);
    }

    public User getUserById(int id) {
        return userDAO.getUserById(id);
    }
}