package me.approximations.music.controllers;

import me.approximations.music.entities.Album;
import me.approximations.music.entities.User;
import me.approximations.music.entities.enums.AccountType;
import me.approximations.music.repositories.AlbumRepository;
import me.approximations.music.repositories.UserRepository;
import me.approximations.music.security.authentication.JwtAuthenticationToken;
import me.approximations.music.security.entities.CustomUserDetails;
import me.approximations.music.services.storage.StorageService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Testcontainers
public class SongControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StorageService storageService;

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

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Test
    public void shouldNotCreateMusicIfNotAuthenticated() throws Exception {
        final File file = new File(Path.of(System.getProperty("user.dir"), "src\\test\\java\\me\\approximations\\music\\controllers\\valid_file.mp3").toString());
        MockMultipartFile multipartFile = new MockMultipartFile("file", "sound.mp3", "audio/mpeg", new FileInputStream(file));

        MockPart name = new MockPart("name", "name".getBytes());
        MockPart imageUrl = new MockPart("imageUrl", "image_url".getBytes());
        MockPart albumId = new MockPart("albumId", "1".getBytes());

        mockMvc.perform(multipart("/song/upload")
                        .file(multipartFile)
                        .part(name, imageUrl, albumId)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void shouldCreateMusic() throws Exception {
        final User user = new User(null, "email", "name", AccountType.GOOGLE);
        final Album album = new Album(null, "name", user);

        userRepository.save(user);
        albumRepository.save(album);

        final File file = new File(Path.of(System.getProperty("user.dir"), "src\\test\\java\\me\\approximations\\music\\controllers\\valid_file.mp3").toString());
        MockMultipartFile multipartFile = new MockMultipartFile("file", "sound.mp3", "audio/mpeg", new FileInputStream(file));

        MockPart name = new MockPart("name", "name".getBytes());
        MockPart imageUrl = new MockPart("imageUrl", "image_url".getBytes());
        MockPart albumId = new MockPart("albumId", "1".getBytes());

        mockMvc.perform(multipart("/song/upload")
                        .file(multipartFile)
                        .part(name, imageUrl, albumId)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("name"))
                .andExpect(jsonPath("$.imageUrl").value("image_url"))
                .andExpect(jsonPath("$.filename").value("sound.mp3"))
                .andExpect(jsonPath("$.songUrl").value("http://localhost:3333/sound.mp3"));

        assertTrue(storageService.objectExists("sound.mp3"));
    }

    private void setAuthenticationOnContext(User user) {
        final JwtAuthenticationToken auth = new JwtAuthenticationToken(new CustomUserDetails(user), true);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    private void setAuthenticationOnContext() {
        setAuthenticationOnContext(new User(1L, "email", "name", AccountType.GOOGLE));
    }

}
