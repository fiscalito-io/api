package io.fiscalito.api.infrastructure.adapters.client;

import io.fiscalito.api.application.ports.outbound.client.OpenIAClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OpenIAClientImplRestClient implements OpenIAClient {

    private final RestTemplate restTemplate;
    private final String openaiApiUrl;
    private final String openaiApiKey;

    @Value("${openai.model:gpt-4o-mini}")
    private String gptModel;

    public OpenIAClientImplRestClient(
            @Value("${openai.api.url}") String openaiApiUrl,
            @Value("${openai.api.key}") String openaiApiKey) {
        this.restTemplate = new RestTemplate();
        this.openaiApiUrl = openaiApiUrl;
        this.openaiApiKey = openaiApiKey;
    }

    @Override
    public String extractDataFromText(String text) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(openaiApiKey);

        Map<String, Object> message = new HashMap<>();
        message.put("role", "system");
        message.put("content", "Eres un asistente especializado en extraer información de facturas de audio. Responde SOLO con el JSON solicitado, sin ningún texto adicional: " + text);
        message.put("role", "user");


        List<Map<String, Object>> messages = new ArrayList<>();
        messages.add(message);

        Map<String, Object> body = new HashMap<>();
        body.put("model", gptModel);
        body.put("messages", messages);
        body.put("response_format", Map.of("type", "json_object"));

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        try {
            var response = restTemplate.postForEntity(
                openaiApiUrl + "/chat/completions",
                request,
                Map.class
            );

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                var choices = (List<Map<String, Object>>) response.getBody().get("choices");
                if (!choices.isEmpty()) {
                    var messageResponse = (Map<String, Object>) choices.get(0).get("message");
                    return (String) messageResponse.get("content");
                }
            }
            throw new RuntimeException("Failed to extract data from text");
        } catch (Exception e) {
            throw new RuntimeException("Error processing text with OpenAI", e);
        }
    }
} 