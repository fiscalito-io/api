package online.tufactura.api.domain.flow;

import lombok.Builder;
import lombok.Data;
import online.tufactura.api.domain.messages.MessageProvider;
import online.tufactura.api.domain.messages.MessageType;

@Data
@Builder
public class FlowCommand {
    private MessageProvider messageProvider;
    private MessageType type;
    private String payload;
    private String from;
    private String messageId;
} 