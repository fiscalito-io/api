package online.tufactura.api.infrastructure.adapters.workflow.states;

import lombok.RequiredArgsConstructor;
import online.tufactura.api.application.ports.inbound.workflow.FlowState;
import org.springframework.context.MessageSource;

import java.util.Locale;

@RequiredArgsConstructor
public abstract class BaseState implements FlowState {

    private final MessageSource messageSource;

    protected String translateMessage(String key, Object[] args, Locale locale) {
        return messageSource.getMessage(key, args, locale);
    }
}
