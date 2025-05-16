package online.tufactura.api.application.ports.outbound.client;

public interface OpenIAClient {
    /**
     * Processes text using OpenAI's API to extract structured data
     * @param text The text to process
     * @return JSON string containing the extracted data
     */
    String extractDataFromText(String text);
}
