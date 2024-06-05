package me.approximations.music.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.approximations.music.entities.Album;
import me.approximations.music.entities.User;
import me.approximations.music.entities.enums.AccountType;
import me.approximations.music.repositories.AlbumRepository;
import me.approximations.music.repositories.UserRepository;
import me.approximations.music.security.authentication.JwtAuthenticationToken;
import me.approximations.music.security.entities.CustomUserDetails;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AlbumRepository albumRepository;
    @MockBean
    private UserRepository userRepository;

    @Test
    public void meShouldNotReturnUserIfUnauthenticated() throws Exception {
        mockMvc.perform(get("/user/me")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void meShouldReturnUserIfAuthenticated() throws Exception {
        setAuthenticationOnContext();

        mockMvc.perform(get("/user/me")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldThrowErrorIfUserNotFoundWhileTryingToListAllAlbums() throws Exception {
        Mockito.when(userRepository.findSimpleById(33L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/user/{id}/album", 33L).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldListAllUserAlbums() throws Exception {
        final User artist = new User(33L, "email", "name", AccountType.GOOGLE);
        final Set<Album> fakeAlbums = Set.of(
                new Album(1L, "Album 1", artist),
                new Album(2L, "Album 2", artist),
                new Album(3L, "Album 3", artist),
                new Album(4L, "Album 4", artist)
        );

        Mockito.when(userRepository.findSimpleById(33L)).thenReturn(Optional.of(artist));
        Mockito.when(albumRepository.getAllByUser(33L)).thenReturn(fakeAlbums);

        final ObjectMapper objectMapper = new ObjectMapper();
        final String fakeAlbumsJson = objectMapper.writeValueAsString(fakeAlbums);

        mockMvc.perform(get("/user/{id}/album", 33L).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(fakeAlbumsJson));
    }

    private void setAuthenticationOnContext(User user) {
        final JwtAuthenticationToken auth = new JwtAuthenticationToken(new CustomUserDetails(user), true);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    private void setAuthenticationOnContext() {
        setAuthenticationOnContext(new User(1L, "email", "name", AccountType.GOOGLE));
    }

}
