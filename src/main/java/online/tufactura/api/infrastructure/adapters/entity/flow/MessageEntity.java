package online.tufactura.api.infrastructure.adapters.entity.flow;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import online.tufactura.api.domain.MessageDirection;
import online.tufactura.api.domain.MessageStatus;
import online.tufactura.api.infrastructure.adapters.entity.BaseBean;

@Entity(name = "messages")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class MessageEntity extends BaseBean {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flow_id", nullable = false)
    private Flow flow;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MessageDirection direction;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MessageStatus status = MessageStatus.PENDING;

    @Column(name = "external_id")
    private String externalId;

    @Column(name = "from_number", nullable = false)
    private String fromNumber;

    @Column(name = "to_number", nullable = false)
    private String toNumber;

    @Column(nullable = false)
    private String provider;

    @Column(nullable = false)
    private String text;

    @Column
    private String meta;

    @Column(name = "message_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private MessageType messageType;
}
