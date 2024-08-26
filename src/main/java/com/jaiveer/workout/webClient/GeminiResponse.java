package com.jaiveer.workout.webClient;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GeminiResponse {
    private List<Candidate> candidates;
    private UsageMetaData usageMetaData;
}
