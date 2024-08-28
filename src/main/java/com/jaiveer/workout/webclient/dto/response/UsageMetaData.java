package com.jaiveer.workout.webclient.dto.response;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UsageMetaData {
    private int promptTokenCount;
    private int candidatesTokenCount;
    private int totalTokenCount;
}
