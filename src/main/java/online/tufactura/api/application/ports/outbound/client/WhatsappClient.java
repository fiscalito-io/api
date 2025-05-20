package online.tufactura.api.application.ports.outbound.client;

import java.io.InputStream;

public interface WhatsappClient {
    /**
     * Sends a message to a WhatsApp number
     * @param phoneNumber The recipient's phone number
     * @param message The message to send
     * @return true if the message was sent successfully
     */
    boolean sendMessage(String phoneNumber, String message);

    /**
     * Gets an audio file from WhatsApp by its ID
     * @param audioId The ID of the audio message
     * @return InputStream containing the audio data
     */
    InputStream getAudioById(String audioId);

    void sendPdf(String phoneNumber, byte[] pdfInvoice, String s);
}
