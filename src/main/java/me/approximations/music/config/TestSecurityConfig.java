package me.approximations.music.config;

import me.approximations.music.handlers.Oauth2SuccessLoginHandler;
import me.approximations.music.security.handlers.SecurityAccessDeniedHandler;
import me.approximations.music.security.handlers.SecurityAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Profile("test")
@Configuration
public class TestSecurityConfig {

    @Bean
    public SecurityFilterChain testFilterChain(HttpSecurity http, Oauth2SuccessLoginHandler oauth2SuccessLoginHandler) throws Exception {
        http.oauth2Login(c -> {
            c.successHandler(oauth2SuccessLoginHandler);
        });

        http.authorizeHttpRequests(c -> c.requestMatchers("/user/me").authenticated());

        http.exceptionHandling(c ->
                c.accessDeniedHandler(new SecurityAccessDeniedHandler())
                        .authenticationEntryPoint(new SecurityAuthenticationEntryPoint())
        );

        http.csrf(c -> c.disable());

        return http.build();
    }
}
