package com.jaiveer.workout.webclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import com.jaiveer.workout.program.WorkoutProgram;
import com.jaiveer.workout.webclient.dto.request.*;
import com.jaiveer.workout.webclient.dto.response.GeminiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;

@Service
public class GeminiApiService {
    private final WebClient webClient;

    @Value("${gemini.api.key}")
    private String secretKey;

    @Autowired
    public GeminiApiService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
//                .baseUrl("https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-pro:generateContent")
                .baseUrl("https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent")

                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    //dont change
    public GeminiResponse generateContent(String prompt) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonSchemaGenerator schemaGenerator = new JsonSchemaGenerator(objectMapper);

        String jsonSchemaString;
        try {
            JsonSchema jsonSchema = schemaGenerator.generateSchema(WorkoutProgram.class);
            jsonSchemaString = objectMapper.writeValueAsString(jsonSchema);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        PromptRequest promptRequest = PromptRequest.builder()
                .name("Create a Workout Program")
                .description(prompt)
                .properties(jsonSchemaString)
                .additionalProperties(false)
                .build();

        String promptRequestString;
        try {
            promptRequestString = objectMapper.writeValueAsString(promptRequest);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        GeminiRequest geminiRequest = GeminiRequest
                .builder()
                .Contents(new ArrayList<>())
                .generationConfig(GenerationConfig.builder().response_mime_type("application/json").build())
                .build();

        geminiRequest.getContents().add(new Content());
        geminiRequest.getContents().get(0).setParts(new ArrayList<>());
        geminiRequest.getContents().get(0).getParts().add(new Part());
        geminiRequest.getContents().get(0).getParts().get(0).setText("Follow JSON schema." + promptRequestString);

        String GeminiRequestString;
        try {
            GeminiRequestString = objectMapper.writeValueAsString(geminiRequest);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        System.out.println(GeminiRequestString);

        return webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("key", secretKey)
                        .build())
                .bodyValue(GeminiRequestString)
                .retrieve()
                .bodyToMono(GeminiResponse.class)
                .block();
    }

}
