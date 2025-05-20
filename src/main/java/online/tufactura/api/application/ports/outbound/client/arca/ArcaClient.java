package online.tufactura.api.application.ports.outbound.client.arca;

public interface ArcaClient {
    byte[] createInvoice(String cuitRepresentado, String invoiceType, String from);
}
