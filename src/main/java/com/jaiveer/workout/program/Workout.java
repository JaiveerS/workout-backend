package com.jaiveer.workout.program;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Setter(AccessLevel.NONE)
    @JsonIgnore
    private Long workoutId;
    private String workoutName;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Exercise> exercises;
}
