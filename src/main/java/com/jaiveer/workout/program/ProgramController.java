package com.jaiveer.workout.program;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jaiveer.workout.program.dto.request.ProgramRequest;
import com.jaiveer.workout.security.JwtService;
import com.jaiveer.workout.webclient.GeminiApiService;

@RestController
@RequestMapping("/api/program")

public class ProgramController {

    ProgramService programService;
    GeminiApiService geminiApiService;
    JwtService jwtService;

    @Autowired
    public ProgramController(ProgramService programService, GeminiApiService geminiApiService, JwtService jwtService) {
        this.programService = programService;
        this.geminiApiService = geminiApiService;
        this.jwtService = jwtService;
    }

    //returns all saved workout program
    @GetMapping("/admin/getAll")
    public ResponseEntity<List<WorkoutProgram>> program() {
        return ResponseEntity.ok(programService.getAllWorkoutPrograms());
    }

    //generates a program and assigns it to a user
    //admin only
    @PostMapping("/admin/create")
    public ResponseEntity<WorkoutProgram> generate(@RequestBody ProgramRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(programService.createWorkoutProgram(request.getPrompt(), request.getUsername()));
    }


    @PostMapping("/user/create")
    public ResponseEntity<WorkoutProgram> generateProgram(@RequestBody String prompt ,@RequestHeader("Authorization") String authorizationHeader) {
        System.out.println(authorizationHeader);
        String jwt = authorizationHeader.substring(7);
        return ResponseEntity.status(HttpStatus.CREATED).body(programService.createWorkoutProgramWithJwt(prompt, jwt));
    }

    //just generates a program using gemini ai api open endpoint
    @PostMapping("/generate")
    public ResponseEntity<WorkoutProgram> generateContent(@RequestBody String prompt) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.CREATED).body(geminiApiService.generateContent(prompt).getCandidates().get(0).getContent().getParts().get(0).convertToWorkoutProgram());
    }
}
