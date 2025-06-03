package io.fiscalito.api.infrastructure.adapters.services;

import io.fiscalito.api.application.ports.outbound.service.EmailService;
import io.fiscalito.api.domain.user.ForgotModel;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@Slf4j
public class EmailServiceImplSMTP implements EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    private final String appUrl;

    public EmailServiceImplSMTP(JavaMailSender mailSender, SpringTemplateEngine templateEngine, @Value("${app.url}") String appUrl) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
        this.appUrl = appUrl;
    }

    @Override
    public void sendWelcomeEmailSignUpByPhone(String email, String name, ForgotModel forgotPasswordToken) {
        try {
            Context context = new Context();
            context.setVariable("name", name);
            context.setVariable("expiresAt", forgotPasswordToken.getExpiresAt());
            context.setVariable("resetLink", this.appUrl+"/reset-password?token=" + forgotPasswordToken.getId());

            String htmlContent = templateEngine.process("email/welcome-set-password", context);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(email);
            helper.setSubject("Bienvenido a Fiscalito 🎉");
            helper.setFrom("no-respond@fiscalito.io");
            helper.setText(htmlContent, true); // HTML

            mailSender.send(message);
            log.info("Email de bienvenida enviado a {}", email);

        } catch (Exception e) {
            log.error("Error enviando email a {}: {}", email, e.getMessage(), e);
            //TODO add custom exception
            throw new RuntimeException("Error enviando el email", e);
        }
    }
}
