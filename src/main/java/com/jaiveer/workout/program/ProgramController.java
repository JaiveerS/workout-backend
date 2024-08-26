package com.jaiveer.workout.program;

import com.jaiveer.workout.user.User;
import com.jaiveer.workout.webClient.GeminiApiService;
import com.jaiveer.workout.webClient.GeminiResponse;
import org.apache.coyote.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/program")

public class ProgramController {

    ProgramService programService;
    GeminiApiService geminiApiService;

    @Autowired
    public ProgramController(ProgramService programService, GeminiApiService geminiApiService) {
        this.programService = programService;
        this.geminiApiService = geminiApiService;
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
    @PostMapping("/create/{username}")
    public ResponseEntity<WorkoutProgram> generate(@RequestBody String prompt,@PathVariable String username) {
        return ResponseEntity.ok(programService.createWorkoutProgram(prompt, username));
    }

    @PostMapping("/generateContent")
    public GeminiResponse generateContent(@RequestBody String prompt) {
        return geminiApiService.generateContent(prompt);
    }
}
