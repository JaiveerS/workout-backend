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
public class Workout {
    @Id
    @GeneratedValue
    private Long workoutId;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Exercise> exercises;
}
