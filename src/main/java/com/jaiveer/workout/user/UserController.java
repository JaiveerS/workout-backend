package com.jaiveer.workout.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public String register(@RequestBody User user) {
        User registeredUser = userService.registerUser(user);
        return registeredUser.toString();
    }


    @GetMapping("/info")
    public String info(@RequestBody String username) {
        return userService.getUser(username).toString();
    }

    @GetMapping("/program")
    public String program(@RequestBody String username) {
        return userService.getUserProgram(username).toString();
    }
}
