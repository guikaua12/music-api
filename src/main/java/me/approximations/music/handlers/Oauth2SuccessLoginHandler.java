package me.approximations.music.handlers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.approximations.music.dtos.CreateUserDTO;
import me.approximations.music.entities.User;
import me.approximations.music.security.jwt.JwtService;
import me.approximations.music.services.user.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
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

    private final CustomUserDetailsService userDetailsService;
    @Value("${spring.security.oauth2.login.redirect-url}")
    private String loginRedirectUrl;
    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (!(authentication.getPrincipal() instanceof OidcUser))
            return;

        final OidcUser oidcUser = (OidcUser) authentication.getPrincipal();

        final User user = getOrCreateUser(oidcUser);

        final String token = jwtService.encode(user);

        response.addCookie(createTokenCookie(token));

        REDIRECT_STRATEGY.sendRedirect(request, response, loginRedirectUrl);
    }

    private User getOrCreateUser(OidcUser oidcUser) {
        User user;
        try {
            user = userDetailsService.findByEmail(oidcUser.getEmail()).getUser();
        } catch (AuthenticationException e) {
            user = userDetailsService.create(new CreateUserDTO(oidcUser.getEmail(), oidcUser.getFullName()));
        }
        return user;
    }

    private Cookie createTokenCookie(String token) {
        final Cookie cookie = new Cookie("music_token", token);
        cookie.setMaxAge((int) COOKIE_TOKEN_DURATION.toMillis());

        return cookie;
    }
}
