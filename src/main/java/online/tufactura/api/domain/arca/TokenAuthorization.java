package online.tufactura.api.domain.arca;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TokenAuthorization {
    private String token;
    private String sign;
    private String cuit;
}
