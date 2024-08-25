package com.jaiveer.workout.program;

import com.jaiveer.workout.user.User;
import com.jaiveer.workout.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProgramService {
    private final WorkoutProgramRepository workoutProgramRepository;
    private final UserService userService;


    @Autowired
    public ProgramService (WorkoutProgramRepository workoutProgramRepository, UserService userService) {
        this.workoutProgramRepository = workoutProgramRepository;
        this.userService = userService;
    }

    //ask gemini ai to make a workout program and return it in json of a specifed format
    //this will create the workout program
    //later have sample programs for gemini to use to build programs
    WorkoutProgram createWorkoutProgram(String username) {
        User user = userService.getUser(username);
        List<WorkoutProgram> workoutPrograms = userService.getUserProgram(username);

        //ask gemini to create workout program json format
        //workoutprogram has these field
        //then workout has these
        //then exercise has these
        //then I just iterate over and create a program correct?

        WorkoutProgram workoutProgram = new WorkoutProgram();
        workoutProgram.setName("test");
        workoutProgram.setUserId(user.getUserid());

        Workout workout = new Workout();
        List<Workout> workouts = new ArrayList<Workout>();
        workouts.add(workout);
        List<Exercise> exercises = new ArrayList<>();
        Exercise exercise = new Exercise();
        exercise.setExerciseName("test");
        exercise.setRepetitions("8-12");
        exercise.setSets(3);
        exercises.add(exercise);
        workout.setExercises(exercises);
        workoutProgram.setWorkouts(workouts);
        workoutPrograms.add(workoutProgram);
        user.setWorkoutPrograms(workoutPrograms);
        userService.updateUser(user);

        return user.getWorkoutPrograms().get(user.getWorkoutPrograms().size() - 1);
    }

    List<WorkoutProgram> getAllWorkoutPrograms() {
        return workoutProgramRepository.findAll();
    }
}
