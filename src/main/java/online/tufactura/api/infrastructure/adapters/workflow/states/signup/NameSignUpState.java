package online.tufactura.api.infrastructure.adapters.workflow.states.signup;

import lombok.extern.slf4j.Slf4j;
import online.tufactura.api.application.ports.outbound.client.WhatsappClient;
import online.tufactura.api.domain.FlowContext;
import online.tufactura.api.domain.FlowStateEnum;
import online.tufactura.api.domain.flow.FlowCommand;
import online.tufactura.api.domain.flow.MessageType;
import online.tufactura.api.infrastructure.adapters.workflow.states.BaseState;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import static online.tufactura.api.domain.FlowStateEnum.SIGN_UP_EMAIL;
import static online.tufactura.api.domain.FlowStateEnum.SIGN_UP_NAME;

@Slf4j
@Component
public class NameSignUpState extends BaseState {
    private final WhatsappClient whatsappClient;

    public NameSignUpState(WhatsappClient whatsappClient, MessageSource messageSource) {
        super(messageSource);
        this.whatsappClient = whatsappClient;
    }

    @Override
    public FlowContext handle(FlowContext context, FlowCommand command) {
        log.debug("Handling name collection state for: {}", command.getFrom());

        if (command.getType().name().equals(MessageType.TEXT.name())) {
            // Store name in context data
            context.setData(command.getPayload());
            context.setCurrentState(SIGN_UP_EMAIL);
            context.setPreviousState(SIGN_UP_NAME);
            whatsappClient.sendMessage(command.getFrom(),
                    translateMessage("singup.request.email",
                            new Object[]{command.getPayload()},
                            context.getLocale()));
        } else {
            whatsappClient.sendMessage(command.getFrom(),
                    "Por favor, envía un mensaje de texto con tu nombre completo.");
        }
        context.setWaitingForResponse(true);
        return context;
    }

    @Override
    public FlowStateEnum getFlowState() {
        return SIGN_UP_NAME;
    }
} 