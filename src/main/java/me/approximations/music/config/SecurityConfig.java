package me.approximations.music.config;

import me.approximations.music.handlers.Oauth2SuccessLoginHandler;
import me.approximations.music.security.filters.JwtAuthenticationFilter;
import me.approximations.music.security.jwt.JwtService;
import me.approximations.music.services.user.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, Oauth2SuccessLoginHandler oauth2SuccessLoginHandler,
                                           JwtService jwtService, UserService userService) throws Exception {
        http.addFilterBefore(new JwtAuthenticationFilter(jwtService, userService), UsernamePasswordAuthenticationFilter.class);
        http.oauth2Login(c -> {
            c.successHandler(oauth2SuccessLoginHandler);
        });

        return http.build();
    }
}
