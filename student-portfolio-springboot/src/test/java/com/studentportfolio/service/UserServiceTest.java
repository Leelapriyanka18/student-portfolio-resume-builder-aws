package com.studentportfolio.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.studentportfolio.dao.UserDAO;
import com.studentportfolio.dto.LoginRequest;
import com.studentportfolio.dto.LoginResponse;
import com.studentportfolio.dto.RegisterRequest;
import com.studentportfolio.model.User;
import com.studentportfolio.util.PasswordUtil;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserDAO userDAO;

    @Test
    void registerNormalizesEmailAndRejectsDuplicates() {
        RegisterRequest request = new RegisterRequest();
        request.setFullName("  Student User  ");
        request.setEmail("  STUDENT@EXAMPLE.COM ");
        request.setPassword("Str0ng!Pass");

        when(userDAO.emailExists("student@example.com")).thenReturn(false);
        when(userDAO.registerUser(argThat(user ->
                "Student User".equals(user.getFullName())
                        && "student@example.com".equals(user.getEmail())
                        && "Str0ng!Pass".equals(user.getPassword())))).thenReturn(true);

        new UserService(userDAO).register(request);

        verify(userDAO).emailExists("student@example.com");
    }

    @Test
    void registerRejectsExistingEmail() {
        RegisterRequest request = new RegisterRequest();
        request.setFullName("Student User");
        request.setEmail("student@example.com");
        request.setPassword("Str0ng!Pass");

        when(userDAO.emailExists("student@example.com")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> new UserService(userDAO).register(request));
    }

    @Test
    void loginReturnsResponseForValidPassword() {
        LoginRequest request = new LoginRequest();
        request.setEmail(" STUDENT@EXAMPLE.COM ");
        request.setPassword("Str0ng!Pass");

        User user = new User();
        user.setId(7);
        user.setFullName("Student User");
        user.setEmail("student@example.com");
        user.setPassword(PasswordUtil.hashPassword("Str0ng!Pass"));

        when(userDAO.getUserByEmail("student@example.com")).thenReturn(user);

        LoginResponse response = new UserService(userDAO).login(request);

        assertEquals(7, response.getUserId());
        assertEquals("Student User", response.getFullName());
        assertEquals("student@example.com", response.getEmail());
    }

    @Test
    void loginRejectsInvalidPassword() {
        LoginRequest request = new LoginRequest();
        request.setEmail("student@example.com");
        request.setPassword("wrong");

        User user = new User();
        user.setPassword(PasswordUtil.hashPassword("Str0ng!Pass"));

        when(userDAO.getUserByEmail("student@example.com")).thenReturn(user);

        assertNull(new UserService(userDAO).login(request));
    }
}
