package io.fiscalito.api.domain.flow;

import io.fiscalito.api.domain.messages.MessageProvider;
import io.fiscalito.api.domain.messages.MessageType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FlowCommand {
    private MessageProvider messageProvider;
    private MessageType type;
    private String payload;
    private String from;
    private String messageId;
} 