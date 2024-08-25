package com.jaiveer.workout.program;

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
    private Long exerciseId;
    @Column(nullable = false)
    private String exerciseName;
    @Column(nullable = false)
    private String sets;
    @Column(nullable = false)
    private String repetitions;
    private String description;
    private int weight;
}
