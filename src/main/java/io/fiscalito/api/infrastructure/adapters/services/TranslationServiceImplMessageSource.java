package io.fiscalito.api.infrastructure.adapters.services;

import io.fiscalito.api.application.ports.outbound.service.TranslationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
@Slf4j
public class TranslationServiceImplMessageSource implements TranslationService {
    private final MessageSource messageSource;

    public String translate(String code, Object[] args, Locale locale) {
        return messageSource.getMessage(code, args, locale);
    }
}
