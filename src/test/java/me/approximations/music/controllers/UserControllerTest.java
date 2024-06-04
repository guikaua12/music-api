package me.approximations.music.controllers;

import me.approximations.music.entities.User;
import me.approximations.music.entities.enums.AccountType;
import me.approximations.music.security.authentication.JwtAuthenticationToken;
import me.approximations.music.security.entities.CustomUserDetails;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void meShouldNotReturnUserIfUnauthenticated() throws Exception {
        mockMvc.perform(get("/user/me")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void meShouldReturnUserIfAuthenticated() throws Exception {
        final JwtAuthenticationToken auth = new JwtAuthenticationToken(new CustomUserDetails(new User(1L, "email", "name", AccountType.GOOGLE)), true);
        SecurityContextHolder.getContext().setAuthentication(auth);

        mockMvc.perform(get("/user/me")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
