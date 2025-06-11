package io.fiscalito.api.application.ports.outbound.client.arca;

import io.fiscalito.api.application.command.CreateInvoiceCommand;
import io.fiscalito.api.domain.arca.wsfev1.CbteTipo;
import io.fiscalito.api.domain.arca.wsfev1.ConceptoTipo;
import io.fiscalito.api.domain.arca.wsfev1.Cotizacion;
import io.fiscalito.api.domain.arca.wsfev1.DocTipo;
import io.fiscalito.api.domain.arca.wsfev1.FECAEResponse;
import io.fiscalito.api.domain.arca.wsfev1.IvaTipo;
import io.fiscalito.api.domain.arca.wsfev1.Moneda;
import io.fiscalito.api.domain.arca.wsfev1.PtoVenta;
import io.fiscalito.api.domain.arca.wsfev1.TributoTipo;

import java.util.List;

public interface WsfeClient {
    FECAEResponse emitInvoice(CreateInvoiceCommand createInvoiceCommand) throws Exception;

    List<TributoTipo> getTiposTributo();

    List<IvaTipo> getTiposIva();

    List<CbteTipo> getTiposCbte();

    List<Moneda> getTiposMoneda();

    List<DocTipo> getTiposDocumento();

    List<ConceptoTipo> getTiposConcepto();

    List<PtoVenta> getPuntosVenta();

    Cotizacion getCotizacion(String monedaId);

}
