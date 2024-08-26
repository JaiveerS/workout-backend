package com.jaiveer.workout.program;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jaiveer.workout.user.User;
import com.jaiveer.workout.user.UserService;
import com.jaiveer.workout.webClient.GeminiApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.ArrayList;
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
    WorkoutProgram createWorkoutProgram(String username) {
        User user = userService.getUser(username);
        List<WorkoutProgram> workoutPrograms = userService.getUserProgram(username);

        Mono<String> program = geminiApiService.generateContent("create a 6 day a week workout program for me. I want it to be a push pull legs split. use current best standards based on the scientific literature for the optimal number of sets per bodypart for both hypertrophy and strength gains. for exercises use science backed best exercises that are attributed the most amount of muscle growth while also taking into account recovery. for rest times provide me with rest times based on how tiring the previous exercise is cosidered in terms of systemic load and based on what the science says is the optimal rest time for muscle growth. make the first batch of push pull leg workouts different from the second.");
        Mono<String> text = extractText(program);
        //comment this out
        System.out.println(text.block());


        WorkoutProgram workoutProgram = saveWorkoutProgram(text.block(), user);

        workoutPrograms.add(workoutProgram);
        user.setWorkoutPrograms(workoutPrograms);
        userService.updateUser(user);

        return user.getWorkoutPrograms().get(user.getWorkoutPrograms().size() - 1);
    }

    List<WorkoutProgram> getAllWorkoutPrograms() {
        return workoutProgramRepository.findAll();
    }

    public Mono<String> extractText(Mono<String> jsonMono) {
        return jsonMono.flatMap(jsonString -> {
            try {
                JsonElement rootElement = JsonParser.parseString(jsonString);
                JsonObject rootObject = rootElement.getAsJsonObject();
                JsonObject firstCandidate = rootObject.getAsJsonArray("candidates").get(0).getAsJsonObject();
                JsonObject contentObject = firstCandidate.getAsJsonObject("content");
                JsonObject partsObject = contentObject.getAsJsonArray("parts").get(0).getAsJsonObject();
                String text = partsObject.get("text").getAsString();
                return Mono.just(text);
            } catch (Exception e) {
                return Mono.error(e);
            }
        });
    }

    public WorkoutProgram saveWorkoutProgram(String jsonData, User user) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            System.out.println("here start");
            WorkoutProgram workoutProgram = objectMapper.readValue(jsonData, WorkoutProgram.class);
            System.out.println("2");
            List<WorkoutProgram> workoutPrograms = user.getWorkoutPrograms();
            System.out.println("3");

            workoutProgram.setUserId(user.getUserid());
            System.out.println("4");

            workoutPrograms.add(workoutProgram);
            System.out.println("5");

            user.setWorkoutPrograms(workoutPrograms);
            userService.updateUser(user);
            System.out.println("Data saved successfully!");
            return workoutProgram;
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error saving data: " + e.getMessage());
        }
        return null;
    }

}
