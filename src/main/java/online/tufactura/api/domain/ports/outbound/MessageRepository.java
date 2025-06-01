package online.tufactura.api.domain.ports.outbound;

import online.tufactura.api.domain.MessageModel;

import java.util.Optional;

public interface MessageRepository {
    void save(MessageModel message);
    Optional<MessageModel> findMessageById(String messageId);
    void delete(String messageId);
}
