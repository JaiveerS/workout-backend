package com.jaiveer.workout.config;

import com.jaiveer.workout.user.UserRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class UserDetailsServiceConfig {

    private UserRepository userRepository;

    public UserDetailsService userDetailsService(){
        return userRepository::findByUsername;
    }
}
