package online.tufactura.api.infrastructure.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import online.tufactura.api.application.ports.outbound.repository.UserRepository;
import online.tufactura.api.domain.AuthenticationProvider;
import online.tufactura.api.domain.UserModel;
import online.tufactura.api.domain.UserRole;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User oauth2User = oauthToken.getPrincipal();
        Map<String, Object> attributes = oauth2User.getAttributes();

        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");
//        String picture = (String) attributes.get("picture");
        String provider = oauthToken.getAuthorizedClientRegistrationId();
        String providerId = (String) attributes.get("sub");

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
        String token = jwtService.generateToken(User.builder()
                .username(user.getEmail())
                .password("")
                .authorities(List.of(new SimpleGrantedAuthority(user.getRole().name())))
                .build());

        String redirectUrl = String.format("%s/auth/callback?token=%s", getFrontendUrl(), token);
        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }

    private String getFrontendUrl() {
        return "http://localhost:3000";
    }
} 