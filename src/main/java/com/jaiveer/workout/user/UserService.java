package com.jaiveer.workout.user;

import com.jaiveer.workout.program.WorkoutProgram;
import com.jaiveer.workout.security.JwtService;
import com.jaiveer.workout.user.dto.request.LoginRequest;
import com.jaiveer.workout.user.dto.response.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, AuthenticationManager authenticationManager, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    //add user password encryption in user class
    public User registerUser(User user){
        if(userRepository.findByUsername(user.getUsername()) != null || userRepository.findByEmail(user.getEmail()) != null){
            return null;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public AuthenticationResponse loginUser(LoginRequest loginRequest){
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        UserDetails user = (UserDetails) auth.getPrincipal();
        String jwt = jwtService.generateToken(user);

        return AuthenticationResponse.
                builder().
                jwtToken(jwt).
                build();
    }

    public User getUserWithJwt(String jwt){
        String username = jwtService.extractUsername(jwt);
        return userRepository.findByUsername(username);
    }

    public User getUser(String username){
        return userRepository.findByUsername(username);
    }

    public List<WorkoutProgram> getWorkoutPrograms(String username){
        return userRepository.findByUsername(username).getWorkoutPrograms();
    }

    public User updateUser(User user){
        return userRepository.save(user);
    }

//    public List<WorkoutProgram> getUserProgram(String username){
//        return userRepository.findByUsername(username).getWorkoutPrograms();
//    }

}
