package online.tufactura.api.domain;

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
