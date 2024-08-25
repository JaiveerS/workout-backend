package com.jaiveer.workout.program;

import com.jaiveer.workout.user.User;
import org.apache.coyote.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/program")

public class ProgramController {

    ProgramService programService;

    @Autowired
    public ProgramController(ProgramService programService) {
        this.programService = programService;
    }

    //returns workout program for a given user
    //each program is attached to a user
    @GetMapping("/get")
    public ResponseEntity<List<WorkoutProgram>> program() {
        return ResponseEntity.ok(programService.getAllWorkoutPrograms());
    }

    //generate program
    //get username
    //get user info
    //get user goals
    //send to gemini api then generate workout program
    @PostMapping("/create")
    public ResponseEntity<WorkoutProgram> generate(@RequestBody String username) {
        return ResponseEntity.ok(programService.createWorkoutProgram(username));
    }
}
