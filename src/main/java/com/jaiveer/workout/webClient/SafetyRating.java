package com.jaiveer.workout.webClient;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SafetyRating {
    private String category;
    private String probability;
}
