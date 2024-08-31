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
@Table(name = "workout_programs")
public class WorkoutProgram {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    @JsonIgnore
    private Long programId;
    @Column(nullable = false)
    private Long userId;
    private String name;
    //client should provide this
    private String duration;
    @Column(length = 1000) // Adjust the length here
    private String description;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Workout> workouts;
}
