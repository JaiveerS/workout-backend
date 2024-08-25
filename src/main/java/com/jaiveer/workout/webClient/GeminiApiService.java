package com.jaiveer.workout.webClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class GeminiApiService {
    private final WebClient webClient;

    @Value("${gemini.api.key}")
    private String secretKey;

    @Autowired
    public GeminiApiService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-pro:generateContent")
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    public String generateContent(String prompt) {
        ObjectMapper objectMapper = new ObjectMapper();
        // Create schema
        ObjectNode schema = objectMapper.createObjectNode();
        schema.put("title", "Workout Program");
        schema.put("description", prompt);
        schema.put("type", "array");

        ObjectNode items = objectMapper.createObjectNode();
        items.put("type", "object");

        ObjectNode properties = objectMapper.createObjectNode();
        ObjectNode workoutName = objectMapper.createObjectNode();
        workoutName.put("description", "Name of the workout.");
        workoutName.put("type", "string");
        properties.set("workout_name", workoutName);

        ObjectNode exercises = objectMapper.createObjectNode();
        exercises.put("description", "List of exercises included in the workout.");
        exercises.put("type", "array");

        ObjectNode exerciseItems = objectMapper.createObjectNode();
        exerciseItems.put("type", "object");

        ObjectNode exerciseProperties = objectMapper.createObjectNode();
        ObjectNode exerciseName = objectMapper.createObjectNode();
        exerciseName.put("description", "Name of the exercise.");
        exerciseName.put("type", "string");
        exerciseProperties.set("exercise_name", exerciseName);

        ObjectNode sets = objectMapper.createObjectNode();
        sets.put("description", "Number of sets for the exercise.");
        sets.put("type", "number");
        exerciseProperties.set("sets", sets);

        ObjectNode repetitions = objectMapper.createObjectNode();
        repetitions.put("description", "Number of repetitions per set.");
        repetitions.put("type", "number");
        exerciseProperties.set("repetitions", repetitions);

        ObjectNode restTime = objectMapper.createObjectNode();
        restTime.put("description", "Rest time between sets in seconds.");
        restTime.put("type", "number");
        exerciseProperties.set("rest_time", restTime);

        exerciseItems.set("properties", exerciseProperties);
        exerciseItems.put("required", objectMapper.createArrayNode().add("exercise_name").add("sets").add("repetitions").add("rest_time"));
        exerciseItems.put("additionalProperties", false);

        exercises.set("items", exerciseItems);
        properties.set("exercises", exercises);

        schema.set("properties", properties);
        schema.put("additionalProperties", false);

        // Convert schema to JSON string
        String schemaJson = schema.toPrettyString();

        ObjectNode partsNode = objectMapper.createObjectNode();
        partsNode.put("text", "Follow JSON schema." +schemaJson);

        ObjectNode contentNode = objectMapper.createObjectNode();
        contentNode.set("parts", objectMapper.createArrayNode().add(partsNode));

        ObjectNode generationConfigNode = objectMapper.createObjectNode();
        generationConfigNode.put("response_mime_type", "application/json");

        ObjectNode requestBodyNode = objectMapper.createObjectNode();
        requestBodyNode.set("contents", objectMapper.createArrayNode().add(contentNode));
        requestBodyNode.set("generationConfig", generationConfigNode);


        String requestBody;
        try {
            requestBody = objectMapper.writeValueAsString(requestBodyNode);
        } catch (Exception e) {
            throw new RuntimeException("Failed to construct JSON request body", e);
        }

        System.out.println(requestBody);

        return webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("key", secretKey)
                        .build())
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

}
