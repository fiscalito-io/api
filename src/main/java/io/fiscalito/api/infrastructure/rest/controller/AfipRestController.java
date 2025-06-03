package io.fiscalito.api.infrastructure.rest.controller;

import io.fiscalito.api.application.ports.outbound.client.arca.WsaaClient;
import io.fiscalito.api.domain.arca.TokenAuthorization;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/afip")
@RequiredArgsConstructor
public class AfipRestController {

    private final WsaaClient wsaaClient;

    @GetMapping("/token")
    public ResponseEntity<TokenAuthorization> getToken() {
        try {
            TokenAuthorization token = wsaaClient.getToken();
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    new TokenAuthorization("ERROR: " + e.getMessage(), "", "")
            );
        }
    }
}
