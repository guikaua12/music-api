package me.approximations.music.config;

import me.approximations.music.handlers.Oauth2SuccessLoginHandler;
import me.approximations.music.security.filters.JwtAuthenticationFilter;
import me.approximations.music.security.handlers.SecurityAccessDeniedHandler;
import me.approximations.music.security.handlers.SecurityAuthenticationEntryPoint;
import me.approximations.music.security.jwt.JwtService;
import me.approximations.music.services.user.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@Profile("default")
@EnableWebSecurity(debug=true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, Oauth2SuccessLoginHandler oauth2SuccessLoginHandler,
                                           JwtService jwtService, CustomUserDetailsService userDetailsService) throws Exception {
        http.addFilterBefore(new JwtAuthenticationFilter(jwtService, userDetailsService), UsernamePasswordAuthenticationFilter.class);
        http.oauth2Login(c -> {
            c.successHandler(oauth2SuccessLoginHandler);
        });

        http.authorizeHttpRequests(c -> c.anyRequest().permitAll());

        http.exceptionHandling(c ->
                c.accessDeniedHandler(new SecurityAccessDeniedHandler())
                        .authenticationEntryPoint(new SecurityAuthenticationEntryPoint())
        );

        http.csrf(c -> c.disable());

        return http.build();
    }
}
