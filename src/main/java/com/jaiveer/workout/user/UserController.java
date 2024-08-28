package com.jaiveer.workout.user;

import com.jaiveer.workout.program.WorkoutProgram;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //need to fix to use spring security
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        User registeredUser = userService.registerUser(user);
        return ResponseEntity.ok(registeredUser);
    }


    @GetMapping("/info/{username}")
    public ResponseEntity<User> info(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUser(username));
    }

    @GetMapping("/programs/{username}")
    public ResponseEntity<List<WorkoutProgram>> programs(@PathVariable String username) {
        return ResponseEntity.ok(userService.getWorkoutPrograms(username));
    }


//    @GetMapping("/program")
//    public ResponseEntity<List<WorkoutProgram>> program(@RequestBody String username) {
//        return ResponseEntity.ok(userService.getUserProgram(username));
//    }
}
