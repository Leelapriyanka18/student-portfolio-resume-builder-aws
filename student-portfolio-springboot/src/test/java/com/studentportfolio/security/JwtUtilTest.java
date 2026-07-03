package com.studentportfolio.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class JwtUtilTest {

    private static final String SECRET = "test-secret-key-with-at-least-32-characters";

    @Test
    void generatedTokenContainsSubjectAndValidExpiration() {
        JwtUtil jwtUtil = new JwtUtil(SECRET, 60_000);

        String token = jwtUtil.generateToken("student@example.com");

        assertEquals("student@example.com", jwtUtil.extractSubject(token));
        assertTrue(jwtUtil.isTokenValid(token, "student@example.com"));
        assertFalse(jwtUtil.isTokenValid(token, "other@example.com"));
    }

    @Test
    void malformedTokenIsRejected() {
        JwtUtil jwtUtil = new JwtUtil(SECRET, 60_000);

        assertNull(jwtUtil.extractSubject("not-a-jwt"));
        assertFalse(jwtUtil.isTokenValid("not-a-jwt", "student@example.com"));
    }
}
