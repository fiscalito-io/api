package io.fiscalito.api.domain.arca;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
public class ArcaInvoiceItem {
    private String code;
    private String description;
    private String quantity;
    private String unitType;
    private BigDecimal unitPrice;
    private BigDecimal bonification;
    private BigDecimal subtotal;
}
