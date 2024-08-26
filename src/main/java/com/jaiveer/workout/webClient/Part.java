package com.jaiveer.workout.webClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jaiveer.workout.program.WorkoutProgram;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Part {
    private String text;

    public WorkoutProgram convertToWorkoutProgram() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return  objectMapper.readValue(this.text, WorkoutProgram.class);
    }
}
