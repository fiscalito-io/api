package io.fiscalito.api.application.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class CreateInvoiceCommand {
    private String taxId;
    private BigDecimal amount;
    private String invoiceType;
    private String toTaxId;
}
