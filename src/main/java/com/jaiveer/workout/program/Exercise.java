package com.jaiveer.workout.program;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
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
