package com.jaiveer.workout.webclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jaiveer.workout.webclient.dto.response.GeminiResponse;
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
//                .baseUrl("https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-pro:generateContent")
                .baseUrl("https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent")

                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    //dont change
    public GeminiResponse generateContent(String prompt) {
        ObjectMapper objectMapper = new ObjectMapper();
        // Create schema
        ObjectNode schema = objectMapper.createObjectNode();
        schema.put("name", "Create a Workout Program");
        schema.put("description", prompt);


        ObjectNode properties = objectMapper.createObjectNode();
        ObjectNode name = objectMapper.createObjectNode();
        ObjectNode duration = objectMapper.createObjectNode();
        ObjectNode workouts = objectMapper.createObjectNode();

        name.put("description", "Name of the program.");
        name.put("type", "string");

        duration.put("description", "Duration of the program.");
        duration.put("type", "string");

        workouts.put("description", "List of workouts included in the program.");
        workouts.put("type", "array");

        properties.set("name", name);
        properties.set("duration", duration);
        properties.set("workouts", workouts);



        ObjectNode workoutName = objectMapper.createObjectNode();
        workoutName.put("description", "Name of the workout.");
        workoutName.put("type", "string");
        workouts.set("workoutName", workoutName); //change this

        workouts.put("description", "List of exercises included in the workout.");
        workouts.put("type", "array");

        ObjectNode exerciseItems = objectMapper.createObjectNode();
        exerciseItems.put("type", "object");

        ObjectNode exerciseProperties = objectMapper.createObjectNode();
        ObjectNode exerciseName = objectMapper.createObjectNode();
        exerciseName.put("description", "Name of the exercise.");
        exerciseName.put("type", "string");
        exerciseProperties.set("exerciseName", exerciseName);

        ObjectNode sets = objectMapper.createObjectNode();
        sets.put("description", "Number of sets for the exercise.");
        sets.put("type", "string");
        exerciseProperties.set("sets", sets);

        ObjectNode repetitions = objectMapper.createObjectNode();
        repetitions.put("description", "Number of repetitions per set.");
        repetitions.put("type", "string");
        exerciseProperties.set("repetitions", repetitions);

        ObjectNode restTime = objectMapper.createObjectNode();
        restTime.put("description", "Rest time between sets in seconds.");
        restTime.put("type", "string");
        exerciseProperties.set("restTime", restTime);

        ObjectNode description = objectMapper.createObjectNode();
        restTime.put("description", "explain the exercise");
        restTime.put("type", "string");
        exerciseProperties.set("description", description);

        exerciseItems.set("properties", exerciseProperties);
        exerciseItems.set("required", objectMapper.createArrayNode().add("exerciseName").add("sets").add("repetitions").add("restTime").add("description"));
        exerciseItems.put("additionalProperties", false);

        workouts.set("exercises", exerciseItems);
        properties.set("workouts", workouts);

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
                .bodyToMono(GeminiResponse.class)
                .block();
    }

}
