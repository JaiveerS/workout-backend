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

    //need to fix to use spring security
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        User registeredUser = userService.registerUser(user);
        if(registeredUser != null) {
            String jwt = jwtService.generateToken(registeredUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(jwt);
        }
        return ResponseEntity.badRequest().body("User Registration failed. Try again.");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.loginUser(request));
    }

    @GetMapping("/info/{username}")
    public ResponseEntity<User> info(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUser(username));
    }

    //for admin only
    @GetMapping("/programs/{username}")
    public ResponseEntity<List<WorkoutProgram>> programs(@PathVariable String username) {
        return ResponseEntity.ok(userService.getWorkoutPrograms(username));
    }
}
