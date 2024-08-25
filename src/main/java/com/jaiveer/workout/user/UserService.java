package com.jaiveer.workout.user;

import com.jaiveer.workout.program.Workout;
import com.jaiveer.workout.program.WorkoutProgram;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    //add user password encryption in user class
    public User registerUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User getUser(String username){
        return userRepository.findByUsername(username);
    }

    public User updateUser(User user){
        return userRepository.save(user);
    }

    public List<WorkoutProgram> getUserProgram(String username){
        return userRepository.findByUsername(username).getWorkoutPrograms();
    }

}
