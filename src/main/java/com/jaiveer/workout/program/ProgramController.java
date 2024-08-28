package com.jaiveer.workout.program;

import com.jaiveer.workout.webclient.GeminiApiService;
import com.jaiveer.workout.webclient.dto.response.GeminiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    //generates a program and assigns it to a user
    @PostMapping("/create/{username}")
    public ResponseEntity<WorkoutProgram> generate(@RequestBody String prompt,@PathVariable String username) {
        return ResponseEntity.ok(programService.createWorkoutProgram(prompt, username));
    }

    //just generates a program using gemini ai api
    @PostMapping("/generateContent")
    public GeminiResponse generateContent(@RequestBody String prompt) {
        return geminiApiService.generateContent(prompt);
    }
}
