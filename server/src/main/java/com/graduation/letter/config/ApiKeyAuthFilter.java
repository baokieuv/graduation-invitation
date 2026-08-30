package com.graduation.letter.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class ApiKeyAuthFilter extends OncePerRequestFilter {

    @Value("${api.security.key}")
    private String expectedApiKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Get the key from the request header
        String providedKey = request.getHeader("X-API-KEY");

        // If the key is valid, tell Spring Security this request is authenticated
        if (expectedApiKey.equals(providedKey)) {
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken("API_KEY_USER", null, Collections.emptyList());

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // Continue the filter chain.
        // If the key was wrong or missing, Spring Security handles blocking the request later.
        filterChain.doFilter(request, response);
    }
}