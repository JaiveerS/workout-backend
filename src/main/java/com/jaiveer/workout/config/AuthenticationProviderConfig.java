package com.jaiveer.workout.config;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AuthenticationProviderConfig {

    private final UserDetailsServiceConfig userDetailsServiceConfig;
    private final PasswordEncoderConfig passwordEncoderConfig;

    @Autowired
    public AuthenticationProviderConfig(UserDetailsServiceConfig userDetailsServiceConfig, PasswordEncoderConfig passwordEncoderConfig) {
        this.userDetailsServiceConfig = userDetailsServiceConfig;
        this.passwordEncoderConfig = passwordEncoderConfig;
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsServiceConfig.userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoderConfig.passwordEncoder());

        return authProvider;
    }
}
