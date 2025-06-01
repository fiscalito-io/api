package online.tufactura.api.infrastructure.adapters.entity.flow;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import online.tufactura.api.domain.messages.MessageProvider;
import online.tufactura.api.domain.flow.WorkflowType;
import online.tufactura.api.infrastructure.adapters.entity.BaseBean;


@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity(name = "flow")
@ToString
@Getter
@Setter
public class Flow extends BaseBean {
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "from")
    private String from;

    @Column(name = "provider")
    private MessageProvider provider;

    @Column(name = "workflow_type")
    private WorkflowType workflowType;

    @Column(name = "status")
    private String status;
}
