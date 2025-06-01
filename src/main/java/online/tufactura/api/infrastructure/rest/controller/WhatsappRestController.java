package online.tufactura.api.infrastructure.rest.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import online.tufactura.api.domain.messages.MessageProvider;
import online.tufactura.api.domain.messages.MessageType;
import online.tufactura.api.domain.flow.FlowCommand;
import online.tufactura.api.infrastructure.adapters.services.FlowExecutor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static online.tufactura.api.infrastructure.utils.ApiConstants.WHATSAPP_PATH;

@RestController
@RequestMapping(WHATSAPP_PATH)
@RequiredArgsConstructor
@Slf4j
public class WhatsappRestController {
    private final FlowExecutor flowExecutor;

    @Value("${whatsapp.webhook.verify-token}")
    private String verifyToken;

    @GetMapping
    public ResponseEntity<String> verifyWebhook(
            @RequestParam(name = "hub.mode") String mode,
            @RequestParam(name = "hub.verify_token") String token,
            @RequestParam(name = "hub.challenge") String challenge) {
        if ("subscribe".equals(mode) && verifyToken.equals(token)) {
            log.info("Webhook verificado correctamente.");
            return ResponseEntity.ok(challenge);
        } else {
            log.warn("Intento de verificación inválido.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid verify token");
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> receiveMessage(@RequestBody Map<String, Object> payload) {
        try {
            List<Map<String, Object>> entries = (List<Map<String, Object>>) payload.get("entry");
            for (Map<String, Object> entry : entries) {
                List<Map<String, Object>> changes = (List<Map<String, Object>>) entry.get("changes");
                for (Map<String, Object> change : changes) {
                    Map<String, Object> value = (Map<String, Object>) change.get("value");
                    List<Map<String, Object>> messages = (List<Map<String, Object>>) value.get("messages");

                    if (messages != null) {
                        for (Map<String, Object> message : messages) {
                            String from = (String) message.get("from");
                            String type = (String) message.get("type");
                            String messageId = (String) message.get("id");

                            String text = null;
                            String audioId = null;

                            if ("text".equals(type)) {
                                Map<String, Object> textObj = (Map<String, Object>) message.get("text");
                                text = textObj != null ? (String) textObj.get("body") : null;
                            } else if ("audio".equals(type)) {
                                Map<String, Object> audioObj = (Map<String, Object>) message.get("audio");
                                audioId = audioObj != null ? (String) audioObj.get("id") : null;
                            }

                            log.info("Mensaje recibido de {}: tipo={}, texto={}, audioId={}", from, type, text, audioId);
                            flowExecutor.execute(FlowCommand.builder()
                                    .messageProvider(MessageProvider.WHATSAPP)
                                    .from(from)
                                    .type(resolveType(type))
                                    .payload("text".equals(type) ? text : audioId)
                                    .messageId(messageId)
                                    .build());
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("Error procesando mensaje de WhatsApp", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error processing message"));
        }

        return ResponseEntity.ok(Map.of("status", "success"));
    }

    private MessageType resolveType(String type) {
        return switch (type) {
            case "text" -> MessageType.TEXT;
            case "audio" -> MessageType.AUDIO;
            default -> MessageType.UNKNOWN;
        };
    }
}
