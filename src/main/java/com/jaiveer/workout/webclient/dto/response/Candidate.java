package com.jaiveer.workout.webclient.dto.response;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Candidate {
    private Content content;
    private String finishReason;
    private int index;
    private List<SafetyRating> safetyRatings;

}
