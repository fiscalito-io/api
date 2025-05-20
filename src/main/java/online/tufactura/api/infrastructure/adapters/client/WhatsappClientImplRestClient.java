package online.tufactura.api.infrastructure.adapters.client;

import online.tufactura.api.application.ports.outbound.client.WhatsappClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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

    @Override
    public void sendPdf(String phoneNumber, byte[] pdfInvoice, String fileName) {
        HttpHeaders mediaHeaders = new HttpHeaders();
        mediaHeaders.setBearerAuth(whatsappToken);
        mediaHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);

        ByteArrayResource pdfResource = new ByteArrayResource(pdfInvoice) {
            @Override
            public String getFilename() {
                return fileName;
            }
        };

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", pdfResource);
        body.add("type", "application/pdf");

        HttpEntity<MultiValueMap<String, Object>> mediaRequest = new HttpEntity<>(body, mediaHeaders);
        ResponseEntity<Map> mediaResponse = restTemplate.postForEntity(
                //TODO aca capaz esta pidiendo el phone number id
                "https://graph.facebook.com/v19.0/" + phoneNumber + "/media",
                mediaRequest,
                Map.class
        );

        String mediaId = (String) mediaResponse.getBody().get("id");

        // 2. Enviar mensaje
        HttpHeaders messageHeaders = new HttpHeaders();
        messageHeaders.setBearerAuth(whatsappToken);
        messageHeaders.setContentType(MediaType.APPLICATION_JSON);

        String payload = """
        {
          "messaging_product": "whatsapp",
          "to": "%s",
          "type": "document",
          "document": {
            "id": "%s",
            "filename": "%s"
          }
        }
        """.formatted(phoneNumber, mediaId, fileName);

        HttpEntity<String> messageRequest = new HttpEntity<>(payload, messageHeaders);
        restTemplate.postForEntity(
                //Idem aca capaz pide el phone number id en lugar del phone number
                "https://graph.facebook.com/v19.0/" + phoneNumber + "/messages",
                messageRequest,
                String.class
        );
    }
} 