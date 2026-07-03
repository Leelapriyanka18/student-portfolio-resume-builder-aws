package com.studentportfolio.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class PasswordUtilTest {

    @Test
    void hashPasswordUsesSaltAndVerifiesOriginalPassword() {
        String firstHash = PasswordUtil.hashPassword("Str0ng!Pass");
        String secondHash = PasswordUtil.hashPassword("Str0ng!Pass");

        assertNotEquals(firstHash, secondHash);
        assertTrue(PasswordUtil.verifyPassword("Str0ng!Pass", firstHash));
        assertFalse(PasswordUtil.verifyPassword("wrong-password", firstHash));
    }

    @Test
    void verifyPasswordRejectsMissingValues() {
        assertFalse(PasswordUtil.verifyPassword(null, "hash"));
        assertFalse(PasswordUtil.verifyPassword("password", null));
    }

    @Test
    void verifyPasswordRejectsMalformedPbkdf2Hash() {
        assertFalse(PasswordUtil.verifyPassword("password", "PBKDF2$abc$not-base64$also-not-base64"));
        assertFalse(PasswordUtil.verifyPassword("password", "PBKDF2$100000$$"));
    }
}
