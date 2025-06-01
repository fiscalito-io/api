package io.fiscalito.api.infrastructure.adapters.workflow.states;

import io.fiscalito.api.application.ports.inbound.workflow.FlowState;
import io.fiscalito.api.application.ports.outbound.service.TranslationService;
import lombok.RequiredArgsConstructor;

import java.util.Locale;

@RequiredArgsConstructor
public abstract class BaseState implements FlowState {

    private final TranslationService translationService;

    protected String translateMessage(String key, Object[] args, Locale locale) {
        return translationService.translate(key, args, locale);
    }
}
