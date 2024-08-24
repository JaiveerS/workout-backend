package com.jaiveer.workout.program;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/program")

public class ProgramController {
    //returns workout program for a given user
    //each program is attached to a user
    @GetMapping("/get")
    public String program() {
        return "returning all workout programs here";
    }

    //generate program
    //get username
    //get user info
    //get user goals
    //send to gemini api then generate workout program
    @PostMapping("create")
    public String generate() {
        return "creating a workout program here";
    }
}
