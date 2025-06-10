package io.fiscalito.api.infrastructure.adapters.client;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import io.fiscalito.api.application.ports.outbound.client.WhatsappClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

@Component
@Slf4j
public class WhatsappClientImplTwilio implements WhatsappClient {

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.whatsapp.from}")
    private String whatsappFrom;

    public WhatsappClientImplTwilio(
            @Value("${twilio.account.sid}") String accountSid,
            @Value("${twilio.auth.token}") String authToken) {
        Twilio.init(accountSid, authToken);
    }

    @Override
    public boolean sendMessage(String phoneNumber, String message) {
        try {
            Message.creator(
                    new PhoneNumber("whatsapp:" + phoneNumber),
                    new PhoneNumber("whatsapp:" + whatsappFrom),
                    message
            ).create();
            log.info("✅ Mensaje enviado a {}", phoneNumber);
            return true;
        } catch (Exception e) {
            log.error("❌ Error enviando mensaje a {}: {}", phoneNumber, e.getMessage(), e);
            return false;
        }
    }

    @Override
    public void sendPdf(String phoneNumber, byte[] pdfInvoice, String fileName) {
        try {
            // Guardar temporalmente el archivo en el sistema
            Path tempFile = Files.createTempFile("invoice-", ".pdf");
            Files.write(tempFile, pdfInvoice, StandardOpenOption.WRITE);

            // Subir el archivo a un CDN o URL pública para que Twilio pueda accederlo
            // (Este paso es solo simulado aquí. Reemplazar con tu lógica real de subida.)
            String publicUrl = "https://your-cdn.com/public/" + fileName;

            Message.creator(
                    new PhoneNumber("whatsapp:" + phoneNumber),
                    new PhoneNumber("whatsapp:" + whatsappFrom),
                    "Aquí está tu factura en PDF: " + fileName
            ).setMediaUrl(URI.create(publicUrl)).create();

            log.info("✅ PDF enviado a {}", phoneNumber);

            // Borrar el archivo temporal (opcional)
            Files.deleteIfExists(tempFile);

        } catch (Exception e) {
            log.error("❌ Error enviando PDF a {}: {}", phoneNumber, e.getMessage(), e);
        }
    }

    @Override
    public InputStream getAudioById(String mediaUrl) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(mediaUrl))
                    .GET()
                    .build();

            HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());

            if (response.statusCode() == 200) {
                return response.body();
            } else {
                throw new RuntimeException("No se pudo descargar el audio. Código HTTP: " + response.statusCode());
            }
        } catch (Exception e) {
            log.error("❌ Error al obtener el audio desde URL {}: {}", mediaUrl, e.getMessage(), e);
            throw new RuntimeException("Error descargando audio", e);
        }
    }
}
