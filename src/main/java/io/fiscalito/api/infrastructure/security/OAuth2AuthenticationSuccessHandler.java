package io.fiscalito.api.infrastructure.security;

import io.fiscalito.api.application.ports.outbound.repository.UserRepository;
import io.fiscalito.api.domain.user.AuthenticationProvider;
import io.fiscalito.api.domain.user.UserModel;
import io.fiscalito.api.domain.user.UserRole;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Value("${frontend.url:http://localhost:5173}")
    private String frontendUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User oauth2User = oauthToken.getPrincipal();
        Map<String, Object> attributes = oauth2User.getAttributes();

        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");
        var provider = AuthenticationProvider.valueOf(oauthToken.getAuthorizedClientRegistrationId().toUpperCase());
        var providerId = (String) attributes.get("sub");

        var optExistingUser = userRepository.findByProviderAndProviderId(provider, providerId);
        UserModel user;

        if (optExistingUser.isPresent()) {
            user = optExistingUser.get();
            user.setName(name);
        } else {
            user = UserModel.builder()
                    .email(email)
                    .name(name)
                    .providerId(providerId)
                    .provider(AuthenticationProvider.GOOGLE)
                    .role(UserRole.USER)
                    .build();
        }
        user.setLastLoginAttempt(Instant.now());
        user = userRepository.saveUser(user);
        String token = jwtService.generateToken(user);

        String redirectUrl = String.format("%s/auth/callback?token=%s", getFrontendUrl(), token);
        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }

    private String getFrontendUrl() {
        return this.frontendUrl;
    }
} 