package online.tufactura.api.infrastructure.rest.controller;

import online.tufactura.api.infrastructure.security.SecurityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static online.tufactura.api.infrastructure.utils.ApiConstants.AUTH_BASE_PATH;
import static online.tufactura.api.infrastructure.utils.ApiConstants.ME;

@RestController
@RequestMapping(AUTH_BASE_PATH)
public class AuthRestController {

    @GetMapping(ME)
    public Object getCurrentUser() {
        return SecurityUtils.getCurrentUser();
    }
}
