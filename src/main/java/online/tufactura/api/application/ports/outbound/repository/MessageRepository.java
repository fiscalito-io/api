package online.tufactura.api.application.ports.outbound.repository;

import online.tufactura.api.domain.messages.MessageModel;

import java.util.Optional;

public interface MessageRepository {
    void save(MessageModel message);
    Optional<MessageModel> findMessageById(String messageId);
    void delete(String messageId);
}