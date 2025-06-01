package io.fiscalito.api.domain.flow;

import lombok.Builder;
import lombok.Data;
import io.fiscalito.api.domain.messages.MessageProvider;
import io.fiscalito.api.domain.messages.MessageType;

@Data
@Builder
public class FlowCommand {
    private MessageProvider messageProvider;
    private MessageType type;
    private String payload;
    private String from;
    private String messageId;
} 