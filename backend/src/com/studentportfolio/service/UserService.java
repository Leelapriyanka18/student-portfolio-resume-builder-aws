package com.studentportfolio.service;

import com.studentportfolio.dao.UserDAO;
import com.studentportfolio.model.User;
import com.studentportfolio.util.PasswordUtil;

public class UserService {

    private UserDAO userDAO;

    public UserService() {
        userDAO = new UserDAO();
    }

    public boolean registerUser(User user) {
        return userDAO.registerUser(user);
    }

    public boolean emailExists(String email) {
        return userDAO.emailExists(email);
    }

    public User getUserByEmail(String email) {
        return userDAO.getUserByEmail(email);
    }

    public User getUserById(int id) {
        return userDAO.getUserById(id);
    }

    public User authenticate(String email, String password) {
        User user = getUserByEmail(email);
        if (user == null) {
            return null;
        }
        return PasswordUtil.verifyPassword(password, user.getPassword()) ? user : null;
    }
}