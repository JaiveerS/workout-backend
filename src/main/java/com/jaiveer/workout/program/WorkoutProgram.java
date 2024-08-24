package com.jaiveer.workout.program;


import com.jaiveer.workout.user.User;
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
    private Long programId;
    private String name;
    private String duration;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Workout> workouts;
}
