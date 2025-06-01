package io.fiscalito.api.application.ports.outbound.client;

import java.io.InputStream;

public interface WhisperClient {
    /**
     * Transcribes an audio file using OpenAI's Whisper model
     * @param audioStream The audio file as an InputStream
     * @return The transcribed text
     */
    String transcribeAudio(InputStream audioStream);
}
