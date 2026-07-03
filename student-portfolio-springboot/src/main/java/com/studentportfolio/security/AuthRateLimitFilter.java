package com.studentportfolio.security;

import java.io.IOException;
import java.time.Clock;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthRateLimitFilter extends OncePerRequestFilter {

    private final Map<String, RequestWindow> attempts = new ConcurrentHashMap<>();
    private final Clock clock;
    private final int maxRequests;
    private final long windowMs;

    public AuthRateLimitFilter(
            @Value("${app.auth.rate-limit.max-requests:20}") int maxRequests,
            @Value("${app.auth.rate-limit.window-ms:60000}") long windowMs) {
        this(maxRequests, windowMs, Clock.systemUTC());
    }

    AuthRateLimitFilter(int maxRequests, long windowMs, Clock clock) {
        this.maxRequests = maxRequests;
        this.windowMs = windowMs;
        this.clock = clock;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        if (!HttpMethod.POST.matches(request.getMethod())) {
            return true;
        }

        String path = request.getRequestURI();
        return !"/api/auth/login".equals(path) && !"/api/auth/register".equals(path);
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String key = request.getRemoteAddr() + ":" + request.getRequestURI();
        long now = clock.millis();

        RequestWindow window = attempts.compute(key, (ignored, current) -> {
            if (current == null || now - current.windowStartMs >= windowMs) {
                return new RequestWindow(now, 1);
            }
            return new RequestWindow(current.windowStartMs, current.count + 1);
        });

        if (window.count > maxRequests) {
            response.setStatus(429);
            response.setContentType("text/plain");
            response.getWriter().write("Too many authentication attempts. Please try again later.");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private record RequestWindow(long windowStartMs, int count) {
    }
}
