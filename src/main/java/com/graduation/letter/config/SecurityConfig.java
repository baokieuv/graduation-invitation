package com.graduation.letter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final ApiKeyAuthFilter apiKeyAuthFilter;

    // Inject our custom filter
    public SecurityConfig(ApiKeyAuthFilter apiKeyAuthFilter) {
        this.apiKeyAuthFilter = apiKeyAuthFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. Disable CSRF as we are building a stateless API
                .csrf(csrf -> csrf.disable())

                // 2. Make the application stateless (no HTTP sessions)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 3. Define URL protection rules
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/templates/**").authenticated()
                        .requestMatchers("/api/v1/guests/**").authenticated()
                        .requestMatchers("/api/v1/invitations/**").permitAll()
                        .anyRequest().authenticated()                      // Default to secure
                )

                // 4. Add our API key filter before standard Spring Security filters
                .addFilterBefore(apiKeyAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
