package com.studentportfolio.security;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import jakarta.servlet.ServletException;

class AuthRateLimitFilterTest {

    @Test
    void limitsRepeatedAuthRequestsFromSameClient() throws ServletException, IOException {
        AuthRateLimitFilter filter = new AuthRateLimitFilter(
                2,
                60_000,
                Clock.fixed(Instant.parse("2026-07-03T00:00:00Z"), ZoneOffset.UTC));

        MockHttpServletResponse first = runRequest(filter);
        MockHttpServletResponse second = runRequest(filter);
        MockHttpServletResponse third = runRequest(filter);

        assertEquals(200, first.getStatus());
        assertEquals(200, second.getStatus());
        assertEquals(429, third.getStatus());
    }

    private MockHttpServletResponse runRequest(AuthRateLimitFilter filter)
            throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/api/auth/login");
        request.setRemoteAddr("203.0.113.10");
        MockHttpServletResponse response = new MockHttpServletResponse();

        filter.doFilter(request, response, new MockFilterChain());

        return response;
    }
}
