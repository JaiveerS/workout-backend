package com.jaiveer.workout.webclient.dto.request;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GeminiRequest {
    private List<Content> Contents;
    private GenerationConfig generationConfig;
}
