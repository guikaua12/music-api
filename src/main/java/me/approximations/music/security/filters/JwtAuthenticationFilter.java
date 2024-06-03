package me.approximations.music.security.filters;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.approximations.music.entities.User;
import me.approximations.music.security.authentication.JwtAuthenticationToken;
import me.approximations.music.security.jwt.JwtService;
import me.approximations.music.services.user.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        token = token.replace("Bearer ", "");

        try {
            final DecodedJWT jwt = jwtService.decode(token);
            final String email = jwt.getClaim("email").asString();

            final Optional<User> userOptional = userService.findByEmail(email);

            if (userOptional.isEmpty()) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            final User user = userOptional.get();

            SecurityContextHolder.getContext().setAuthentication(new JwtAuthenticationToken(user));
            filterChain.doFilter(request, response);
        } catch (JWTVerificationException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
    }
}
