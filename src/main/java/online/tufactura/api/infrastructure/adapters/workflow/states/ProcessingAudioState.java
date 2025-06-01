package online.tufactura.api.infrastructure.adapters.workflow.states;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import online.tufactura.api.application.ports.inbound.workflow.FlowState;
import online.tufactura.api.application.ports.outbound.client.WhatsappClient;
import online.tufactura.api.application.ports.outbound.client.WhisperClient;
import online.tufactura.api.domain.FlowContext;
import online.tufactura.api.domain.FlowStateEnum;
import online.tufactura.api.domain.flow.FlowCommand;
import org.springframework.stereotype.Component;

import java.io.InputStream;

import static online.tufactura.api.domain.FlowStateEnum.FINISHED_ERROR;
import static online.tufactura.api.domain.FlowStateEnum.PROCESSING_AUDIO;
import static online.tufactura.api.domain.FlowStateEnum.PROCESSING_TEXT;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProcessingAudioState implements FlowState {
    private final WhatsappClient whatsappClient;
    private final WhisperClient whisperClient;

    @Override
    public FlowContext handle(FlowContext context, FlowCommand command) {
        log.debug("Processing audio in ProcessingAudioState for phone number: {}", command.getFrom());
        
        try {
            // Get audio from WhatsApp
            InputStream audioStream = whatsappClient.getAudioById(command.getPayload());
            
            // Transcribe audio
            String transcription = whisperClient.transcribeAudio(audioStream);
            
            // Store transcription in context
            context.setData(transcription);
            context.setCurrentState(PROCESSING_TEXT);
            context.setPreviousState(PROCESSING_AUDIO);
            
            whatsappClient.sendMessage(command.getFrom(),
                "Audio procesado correctamente. Procesando la información...");
            return context;
        } catch (Exception e) {
            log.error("Error processing audio: {}", e.getMessage(), e);
            context.setCurrentState(FINISHED_ERROR);
            whatsappClient.sendMessage(command.getFrom(),
                "Lo siento, hubo un error procesando tu audio. Por favor, intenta de nuevo.");
            return context;
        }
    }

    @Override
    public FlowStateEnum getFlowState() {
        return PROCESSING_AUDIO;
    }
} 