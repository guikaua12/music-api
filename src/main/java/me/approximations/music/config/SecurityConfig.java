package me.approximations.music.config;

import me.approximations.music.handlers.Oauth2SuccessLoginHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, Oauth2SuccessLoginHandler oauth2SuccessLoginHandler) throws Exception {
        http.oauth2Login(c -> c.successHandler(oauth2SuccessLoginHandler));

        return http.build();
    }
}
