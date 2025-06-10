package io.fiscalito.api.infrastructure.rest.controller;

import io.fiscalito.api.application.command.CreateInvoiceCommand;
import io.fiscalito.api.application.ports.inbound.service.ArcaService;
import io.fiscalito.api.domain.arca.wsfev1.FECAEResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/afip/token")
@RequiredArgsConstructor
@Slf4j
public class InvoiceController {

    private final ArcaService arcaService;

    @PostMapping
    public ResponseEntity<FECAEResponse> createInvoice(@RequestBody CreateInvoiceCommand command) {
        try {
            arcaService.createInvoice(command);
            return ResponseEntity.status(204).build();
        } catch (Exception e) {
            log.error("Error al crear factura", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
