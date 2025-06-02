package io.fiscalito.api.domain.flow;

import io.fiscalito.api.domain.BaseModel;
import io.fiscalito.api.domain.messages.MessageProvider;
import io.fiscalito.api.domain.messages.MessageType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

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
