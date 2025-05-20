package online.tufactura.api.domain.models.flow.states;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import online.tufactura.api.domain.models.FlowContext;
import online.tufactura.api.domain.models.flow.FlowCommand;
import online.tufactura.api.domain.models.flow.FlowState;
import online.tufactura.api.domain.ports.outbound.client.WhisperClient;
import online.tufactura.api.domain.ports.outbound.client.WhatsappClient;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProcessingAudioState implements FlowState {
    private final WhatsappClient whatsappClient;
    private final WhisperClient whisperClient;

    @Override
    public void handle(FlowContext context, FlowCommand command) {
        log.debug("Processing audio in ProcessingAudioState for phone number: {}", command.getPhoneNumber());
        
        try {
            // Get audio from WhatsApp
            InputStream audioStream = whatsappClient.getAudioById(command.getPayload());
            
            // Transcribe audio
            String transcription = whisperClient.transcribeAudio(audioStream);
            
            // Store transcription in context
            context.setData(transcription);
            context.setCurrentState("PROCESSING_TEXT");
            context.setPreviousState("PROCESSING_AUDIO");
            
            whatsappClient.sendMessage(command.getPhoneNumber(), 
                "Audio procesado correctamente. Procesando la información...");
        } catch (Exception e) {
            log.error("Error processing audio: {}", e.getMessage(), e);
            context.setCurrentState("INITIAL");
            whatsappClient.sendMessage(command.getPhoneNumber(), 
                "Lo siento, hubo un error procesando tu audio. Por favor, intenta de nuevo.");
        }
    }

    @Override
    public String getStateName() {
        return "PROCESSING_AUDIO";
    }
} 