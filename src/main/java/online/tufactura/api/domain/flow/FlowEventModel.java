package online.tufactura.api.domain.flow;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import online.tufactura.api.domain.BaseModel;
import online.tufactura.api.domain.messages.MessageProvider;
import online.tufactura.api.domain.messages.MessageType;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
public class FlowEventModel extends BaseModel {
    private MessageProvider provider;
    private String from;
    private String text;
    private MessageType type;
    private String audioId;

}
