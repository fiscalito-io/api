package io.fiscalito.api.application.ports.outbound.service;

public interface WhatsappService {
    Boolean isAudioMessage(String messageType);
    byte[] getAudioFromMessage(String messageId);
    void sendMessage(String to, String message);
}
