package me.approximations.music.handlers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.approximations.music.dtos.CreateUserDTO;
import me.approximations.music.entities.User;
import me.approximations.music.security.jwt.JwtService;
import me.approximations.music.services.user.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;

@RequiredArgsConstructor
@Component
public class Oauth2SuccessLoginHandler implements AuthenticationSuccessHandler {
    private static final RedirectStrategy REDIRECT_STRATEGY = new DefaultRedirectStrategy();
    private static final Duration COOKIE_TOKEN_DURATION = Duration.ofHours(1);

    private final UserService userService;
    @Value("${spring.security.oauth2.login.redirect-url}")
    private String loginRedirectUrl;
    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (!(authentication.getPrincipal() instanceof OidcUser))
            return;

        final OidcUser oidcUser = (OidcUser) authentication.getPrincipal();

        final User user = userService.findByEmail(oidcUser.getEmail()).orElseGet(() ->
                userService.create(new CreateUserDTO(oidcUser.getEmail(), oidcUser.getFullName()))
        );

        final String token = jwtService.encode(user);

        response.addCookie(createTokenCookie(token));

        REDIRECT_STRATEGY.sendRedirect(request, response, loginRedirectUrl);
    }

    private Cookie createTokenCookie(String token) {
        final Cookie cookie = new Cookie("music_token", token);
        cookie.setMaxAge((int) COOKIE_TOKEN_DURATION.toMillis());

        return cookie;
    }
}
