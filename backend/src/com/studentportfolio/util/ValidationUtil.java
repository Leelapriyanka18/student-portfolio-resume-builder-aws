package com.studentportfolio.util;

import java.util.regex.Pattern;

public class ValidationUtil {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private static final int PASSWORD_MIN_LENGTH = 8;

    public static boolean isValidEmail(String email) {
        return email != null && !email.isBlank() && EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= PASSWORD_MIN_LENGTH;
    }

    public static boolean isRequiredFieldValid(String field) {
        return field != null && !field.isBlank();
    }
}
