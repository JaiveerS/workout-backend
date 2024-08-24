package com.jaiveer.workout.user;

import com.jaiveer.workout.program.Workout;
import com.jaiveer.workout.program.WorkoutProgram;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //add user password encryption in user class
    public User registerUser(User user){
        return userRepository.save(user);
    }

    public User getUser(String username){
        return userRepository.findByUsername(username);
    }

    public List<WorkoutProgram> getUserProgram(String username){
        return userRepository.findByUsername(username).getWorkoutPrograms();
    }

}
