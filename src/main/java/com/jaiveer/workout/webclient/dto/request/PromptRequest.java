package com.jaiveer.workout.webclient.dto.request;

import lombok.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PromptRequest {
    private String name;
    private String description;
    private String properties;
    private boolean additionalProperties;
}
