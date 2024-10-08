package com.jaiveer.workout.program;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
@Table(name = "exercises")
public class Exercise {
    @Id
    @GeneratedValue
    @JsonIgnore
    @Setter(AccessLevel.NONE)
    private Long exerciseId;
    @Column(nullable = false)
    private String exerciseName;
    @Column(nullable = false)
    private String sets;
    @Column(nullable = false)
    private String repetitions;
    @Column(length = 1000) // Adjust the length here
    private String description;
    private String restTime;
    private int weight;
}
