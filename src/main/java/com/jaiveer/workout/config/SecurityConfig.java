package com.jaiveer.workout.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        // Allow all requests to be authorized
        http
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().permitAll()  // Authorize all requests
                )
                .csrf(AbstractHttpConfigurer::disable);  // Disable CSRF protection done for stateless rest apis

        return http.build();
    }
}
