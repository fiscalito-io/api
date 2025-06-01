package io.fiscalito.api.application.ports.outbound.service;

import java.util.Locale;

public interface TranslationService {
    String translate(String code, Object[] args, Locale locale);
}
