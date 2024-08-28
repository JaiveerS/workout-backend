package com.jaiveer.workout.program;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jaiveer.workout.user.User;
import com.jaiveer.workout.user.UserService;
import com.jaiveer.workout.webclient.GeminiApiService;
import com.jaiveer.workout.webclient.dto.response.GeminiResponse;
import com.jaiveer.workout.webclient.dto.response.Part;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgramService {
    private final WorkoutProgramRepository workoutProgramRepository;
    private final UserService userService;
    private final GeminiApiService geminiApiService;


    @Autowired
    public ProgramService (WorkoutProgramRepository workoutProgramRepository, UserService userService, GeminiApiService geminiApiService) {
        this.workoutProgramRepository = workoutProgramRepository;
        this.userService = userService;
        this.geminiApiService = geminiApiService;
    }

    //ask gemini ai to make a workout program and return it in json of a specifed format
    //this will create the workout program
    //later have sample programs for gemini to use to build programs
    WorkoutProgram createWorkoutProgram(String prompt, String username) {
        User user = userService.getUser(username);
        List<WorkoutProgram> workoutPrograms = userService.getWorkoutPrograms(username);

        GeminiResponse response = geminiApiService.generateContent(prompt);
        System.out.println(response.toString());
        Part text = response.getCandidates().get(0).getContent().getParts().get(0);
        //comment this out
        System.out.println(response.getCandidates().get(0).getContent().getParts().get(0).getText());

        WorkoutProgram workoutProgramTemp = null;
        try {
            workoutProgramTemp = response.getCandidates().get(0).getContent().getParts().get(0).convertToWorkoutProgram();
            System.out.println("converted to workoutProgram");
        } catch (JsonProcessingException e) {
            System.out.println("failed to convert to workoutProgram");
            throw new RuntimeException(e);
        }

        workoutProgramTemp.setUserId(user.getUserid());
        workoutPrograms.add(workoutProgramTemp);
        user.setWorkoutPrograms(workoutPrograms);
        userService.updateUser(user);

        return user.getWorkoutPrograms().get(user.getWorkoutPrograms().size() - 1);
    }

    List<WorkoutProgram> getAllWorkoutPrograms() {
        return workoutProgramRepository.findAll();
    }


}
