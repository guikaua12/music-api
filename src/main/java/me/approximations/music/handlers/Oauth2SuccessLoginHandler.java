package me.approximations.music.handlers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.approximations.music.dtos.CreateUserDTO;
import me.approximations.music.entities.User;
import me.approximations.music.services.user.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class Oauth2SuccessLoginHandler implements AuthenticationSuccessHandler {
    public static final RedirectStrategy REDIRECT_STRATEGY = new DefaultRedirectStrategy();
    private final UserService userService;
    @Value("${spring.security.oauth2.login.redirect-url}")
    private String loginRedirectUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (!(authentication.getPrincipal() instanceof OidcUser))
            return;

        final OidcUser oidcUser = (OidcUser) authentication.getPrincipal();

        final Optional<User> userOptional = userService.findByEmail(oidcUser.getEmail());
        if (userOptional.isEmpty()) {
            userService.create(new CreateUserDTO(oidcUser.getEmail(), oidcUser.getFullName()));
        }

        REDIRECT_STRATEGY.sendRedirect(request, response, loginRedirectUrl);
    }
}
