package online.tufactura.api.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class MessageModel {
    private String id;
    private String phoneNumber;
    private MessageProvider provider;
    private String jsonData; // JSON string to store any additional data needed by the message
}
