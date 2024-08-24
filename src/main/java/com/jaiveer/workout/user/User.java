package com.jaiveer.workout.user;

import com.jaiveer.workout.program.WorkoutProgram;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private Long userid;
    @Column(unique = true,nullable  = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String firstname;
    @Column(nullable = false)
    private String lastname;
    private int age;
    private String gender;
    private double weight;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<WorkoutProgram> workoutPrograms;

    @Override
    public String toString(){
        return username + " " + firstname + " " + lastname + " " + age + " " + gender + " " + weight + " " + email;
    }
}
