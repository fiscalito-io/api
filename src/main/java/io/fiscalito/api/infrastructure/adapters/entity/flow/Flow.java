package io.fiscalito.api.infrastructure.adapters.entity.flow;

import io.fiscalito.api.domain.flow.WorkflowType;
import io.fiscalito.api.domain.messages.MessageProvider;
import io.fiscalito.api.infrastructure.adapters.entity.BaseBean;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;


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
