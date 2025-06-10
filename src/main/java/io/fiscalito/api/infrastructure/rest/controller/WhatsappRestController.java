package io.fiscalito.api.infrastructure.rest.controller;

import io.fiscalito.api.domain.flow.FlowCommand;
import io.fiscalito.api.domain.messages.MessageType;
import io.fiscalito.api.infrastructure.adapters.services.FlowExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static io.fiscalito.api.domain.messages.MessageProvider.WHATSAPP;
import static io.fiscalito.api.infrastructure.utils.ApiConstants.WHATSAPP_PATH;

@RestController
@RequestMapping(WHATSAPP_PATH)
@Slf4j
public class WhatsappRestController {

    private final String whatsappFrom;

    private final FlowExecutor flowExecutor;

    public WhatsappRestController(@Value("${twilio.whatsapp.from}") String whatsappFrom, FlowExecutor flowExecutor) {
        this.whatsappFrom = whatsappFrom;
        this.flowExecutor = flowExecutor;
    }


    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void handleIncomingMessage(@RequestParam Map<String, String> params) {
        String from = params.get("From").replace("whatsapp:", "");
        String body = params.get("Body");
        String mediaUrl = params.get("MediaUrl0");
        String mediaType = params.get("MediaContentType0");
        String messageSid = params.get("MessageSid");
        if (whatsappFrom.equals(from)) {
            log.warn(whatsappFrom + " from " + from);
            return; // Ignore messages from our own WhatsApp number
        }
        var builder = FlowCommand.builder()
                .from(from)
                .messageId(messageSid)
                .messageProvider(WHATSAPP);
        if (mediaUrl != null && !mediaUrl.isEmpty() && mediaType != null) {
            if (mediaType.startsWith("audio/")) {
                builder.type(MessageType.AUDIO);
                builder.payload(mediaUrl);
                log.info("🎧 Audio recibido de {}: {}", from, mediaUrl);
            } else {
                log.warn("📎 Tipo de media no soportado: {}", mediaType);
                return;
            }
        } else {
            builder.type(MessageType.TEXT);
            builder.payload(body);
            log.info("💬 Texto recibido de {}: {}", from, body);
        }
        FlowCommand command = builder.build();
        log.info("📦 FlowCommand creado: {}", command);
        flowExecutor.execute(command);
    }
}
