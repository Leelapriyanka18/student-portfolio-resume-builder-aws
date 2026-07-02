package com.studentportfolio.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    private final javax.crypto.SecretKey signingKey;
    private final long expirationMs;

    public JwtUtil(
            @Value("${app.jwt.secret}") String secret,
            @Value("${app.jwt.expiration-ms}") long expirationMs) {
        this.signingKey = Keys.hmacShaKeyFor(secret.getBytes(java.nio.charset.StandardCharsets.UTF_8));
        this.expirationMs = expirationMs;
    }

    public String generateToken(String subject) {
        Date issuedAt = new Date();
        Date expiresAt = new Date(issuedAt.getTime() + expirationMs);

        return Jwts.builder()
                .subject(subject)
                .issuedAt(issuedAt)
                .expiration(expiresAt)
                .signWith(signingKey)
                .compact();
    }

    public String extractSubject(String token) {
        Claims claims = extractClaims(token);
        return claims == null ? null : claims.getSubject();
    }

    public boolean isTokenValid(String token, String expectedSubject) {
        if (expectedSubject == null) {
            return false;
        }

        Claims claims = extractClaims(token);
        if (claims == null) {
            return false;
        }

        Date expiration = claims.getExpiration();
        return expectedSubject.equals(claims.getSubject())
                && expiration != null
                && expiration.after(new Date());
    }

    private Claims extractClaims(String token) {
        if (token == null || token.isBlank()) {
            return null;
        }

        try {
            return Jwts.parser()
                    .verifyWith(signingKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (JwtException | IllegalArgumentException ex) {
            return null;
        }
    }
}
