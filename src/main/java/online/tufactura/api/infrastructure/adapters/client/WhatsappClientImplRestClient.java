package online.tufactura.api.infrastructure.adapters.client;

import online.tufactura.api.application.ports.outbound.client.WhatsappClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Component
public class WhatsappClientImplRestClient implements WhatsappClient {

    private final RestTemplate restTemplate;
    private final String whatsappApiUrl;
    private final String whatsappToken;

    public WhatsappClientImplRestClient(
            @Value("${whatsapp.api.url}") String whatsappApiUrl,
            @Value("${whatsapp.api.token}") String whatsappToken) {
        this.restTemplate = new RestTemplate();
        this.whatsappApiUrl = whatsappApiUrl;
        this.whatsappToken = whatsappToken;
    }

    @Override
    public boolean sendMessage(String phoneNumber, String message) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(whatsappToken);

        Map<String, Object> body = new HashMap<>();
        body.put("messaging_product", "whatsapp");
        body.put("to", phoneNumber);
        body.put("type", "text");
        
        Map<String, String> text = new HashMap<>();
        text.put("body", message);
        body.put("text", text);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        
        try {
            restTemplate.postForEntity(whatsappApiUrl + "/messages", request, String.class);
            return true;
        } catch (Exception e) {
            // Log the error
            return false;
        }
    }

    @Override
    public InputStream getAudioById(String audioId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(whatsappToken);

        HttpEntity<Void> request = new HttpEntity<>(headers);
        
        try {
            return restTemplate.execute(
                whatsappApiUrl + "/media/" + audioId,
                org.springframework.http.HttpMethod.GET,
                clientHttpResponse -> clientHttpResponse.getBody(),
                null
            );
        } catch (Exception e) {
            // Log the error
            throw new RuntimeException("Failed to get audio from WhatsApp", e);
        }
    }
} 