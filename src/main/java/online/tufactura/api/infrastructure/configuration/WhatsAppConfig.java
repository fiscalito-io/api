package online.tufactura.api.infrastructure.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WhatsAppConfig {

    @Value("${whatsapp.webhook.verify-token}")
    private String verifyToken;

    public String getVerifyToken() {
        return verifyToken;
    }
} 