package com.jaiveer.workout.webclient.dto.response;

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
