package com.jaiveer.workout.program;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
@Table(name = "workouts")
public class Workout {
    @Id
    @GeneratedValue
    private Long workoutId;
    private String workoutName;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Exercise> exercises;
}
