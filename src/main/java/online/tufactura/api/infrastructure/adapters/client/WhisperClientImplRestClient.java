package online.tufactura.api.infrastructure.adapters.client;

import online.tufactura.api.application.ports.outbound.client.WhisperClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Component
public class WhisperClientImplRestClient implements WhisperClient {

    private final RestTemplate restTemplate;
    private final String openaiApiUrl;
    private final String openaiApiKey;

    public WhisperClientImplRestClient(
            @Value("${openai.api.url}") String openaiApiUrl,
            @Value("${openai.api.key}") String openaiApiKey) {
        this.restTemplate = new RestTemplate();
        this.openaiApiUrl = openaiApiUrl;
        this.openaiApiKey = openaiApiKey;
    }

    @Override
    public String transcribeAudio(InputStream audioStream) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.setBearerAuth(openaiApiKey);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new org.springframework.core.io.InputStreamResource(audioStream));
        body.add("model", "whisper-1");

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);

        try {
            var response = restTemplate.postForEntity(
                openaiApiUrl + "/audio/transcriptions",
                request,
                Map.class
            );
            
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return (String) response.getBody().get("text");
            }
            throw new RuntimeException("Failed to transcribe audio");
        } catch (Exception e) {
            throw new RuntimeException("Error transcribing audio", e);
        } finally {
            try {
                audioStream.close();
            } catch (IOException e) {
                // Log error
            }
        }
    }
} 