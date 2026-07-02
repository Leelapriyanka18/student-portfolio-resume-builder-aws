package com.studentportfolio.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.studentportfolio.dao.UserDAO;
import com.studentportfolio.model.User;

@Service
public class AuthenticatedUserService {

    private final UserDAO userDAO;

    public AuthenticatedUserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public int getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication.getName() == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }

        User user = userDAO.getUserByEmail(authentication.getName());

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }

        return user.getId();
    }
}
