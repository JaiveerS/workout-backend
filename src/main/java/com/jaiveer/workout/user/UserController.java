package com.jaiveer.workout.user;

import com.jaiveer.workout.program.WorkoutProgram;
import com.jaiveer.workout.security.JwtService;
import com.jaiveer.workout.user.dto.request.LoginRequest;
import com.jaiveer.workout.user.dto.response.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    UserService userService;
    JwtService jwtService;

    @Autowired
    public UserController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    //creates and adds user to db
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        User registeredUser = userService.registerUser(user);
        if(registeredUser != null) {
            String jwt = jwtService.generateToken(registeredUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(jwt);
        }
        return ResponseEntity.badRequest().body("User Registration failed. Try again.");
    }

    //generates a jwt for valid users
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.loginUser(request));
    }

    //returns user details
    @GetMapping("/info")
    public ResponseEntity<User> info(@RequestHeader("Authorization") String authorizationHeader) {
        String jwt = authorizationHeader.substring(7);
        return ResponseEntity.ok(userService.getUserWithJwt(jwt));
    }

    //fetches users saved programs
    @GetMapping("/programs")
    public ResponseEntity<List<WorkoutProgram>> programs(@RequestHeader("Authorization") String authorizationHeader) {
        String jwt = authorizationHeader.substring(7);
        String username = jwtService.extractUsername(jwt);
        return ResponseEntity.ok(userService.getWorkoutPrograms(username));
    }
}
