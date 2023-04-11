package com.reviewer.filters;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SessionAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        System.out.println("Got in here");
        System.out.println(JwtUtils.generateToken("sanjeev"));
        String sessionId = getSessionIdFromCookie(request);
        if (true) {
            if (true) {
                // The user is authenticated, so allow the request to proceed
                filterChain.doFilter(request, response);
                return;
            }
        }

        // The user is not authenticated, so return a 401 Unauthorized response
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    private String getSessionIdFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("sessionId")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}