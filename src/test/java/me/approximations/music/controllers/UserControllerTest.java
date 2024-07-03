package me.approximations.music.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.approximations.music.entities.Album;
import me.approximations.music.entities.User;
import me.approximations.music.entities.enums.AccountType;
import me.approximations.music.repositories.AlbumRepository;
import me.approximations.music.repositories.UserRepository;
import me.approximations.music.security.authentication.JwtAuthenticationToken;
import me.approximations.music.security.entities.CustomUserDetails;
import me.approximations.music.utils.RepositoryCleanUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RepositoryCleanUtil repositoryCleanUtil;

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:16-alpine"
    );

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @BeforeEach
    public void beforeEach() {
        repositoryCleanUtil.clean();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

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
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("email"))
                .andExpect(jsonPath("$.name").value("name"));
    }

    @Test
    public void shouldNotReturnAlbumsIfUserNotExists() throws Exception {
        mockMvc.perform(get("/user/{id}/album", 1L).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldListAllUserAlbums() throws Exception {
        final User artist = new User(1L, "email", "name", AccountType.GOOGLE);
        final List<Album> fakeAlbums = List.of(
                new Album(null, "Album 1", artist),
                new Album(null, "Album 2", artist),
                new Album(null, "Album 3", artist),
                new Album(null, "Album 4", artist)
        );

        userRepository.save(artist);
        albumRepository.saveAll(fakeAlbums);

        final ObjectMapper objectMapper = new ObjectMapper();
        final String fakeAlbumsJson = objectMapper.writeValueAsString(fakeAlbums);

        mockMvc.perform(get("/user/{id}/albums", 1L).accept(MediaType.APPLICATION_JSON))
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
