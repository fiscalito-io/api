package io.fiscalito.api.application.ports.outbound.repository;

import io.fiscalito.api.domain.messages.MessageModel;

import java.util.Optional;

public interface MessageRepository {
    void save(MessageModel message);
    Optional<MessageModel> findMessageById(String messageId);
    void delete(String messageId);
}