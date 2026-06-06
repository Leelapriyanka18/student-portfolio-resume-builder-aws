package com.studentportfolio.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.studentportfolio.dao.UserDAO;
import com.studentportfolio.dto.LoginRequest;
import com.studentportfolio.dto.RegisterRequest;
import com.studentportfolio.model.User;
import com.studentportfolio.util.PasswordUtil;

@Service
public class UserService {

    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Transactional
    public void register(RegisterRequest request) {

        String email = request.getEmail().trim().toLowerCase();

        if (userDAO.emailExists(email)) {
            throw new IllegalArgumentException("Email is already registered");
        }

        User user = new User();
        user.setFullName(request.getFullName().trim());
        user.setEmail(email);
        user.setPassword(request.getPassword());

        boolean created = userDAO.registerUser(user);

        if (!created) {
            throw new IllegalStateException("Unable to register user");
        }
    }

    public boolean login(LoginRequest request) {

        String email = request.getEmail().trim().toLowerCase();

        User user = userDAO.getUserByEmail(email);

        if (user == null) {
            return false;
        }

        return PasswordUtil.verifyPassword(
                request.getPassword(),
                user.getPassword()
        );
    }
}