package com.jaiveer.workout.webClient;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Content {
    private List<Part> parts;
    private String role;
}
