package me.approximations.music.services;

import jakarta.validation.ConstraintViolationException;
import me.approximations.music.dtos.CreateAlbumDTO;
import me.approximations.music.entities.Album;
import me.approximations.music.entities.User;
import me.approximations.music.entities.enums.AccountType;
import me.approximations.music.exceptions.NotFoundException;
import me.approximations.music.repositories.AlbumRepository;
import me.approximations.music.repositories.UserRepository;
import me.approximations.music.services.album.AlbumService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
public class AlbumServiceTest {
    @Autowired
    private AlbumService albumService;
    @MockBean
    private AlbumRepository albumRepository;
    @MockBean
    private UserRepository userRepository;

    @Test
    public void shouldNotFindByIdIfAlbumNotExists() {
        Mockito.when(albumRepository.findById(33L)).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> albumService.findById(33L));
    }

    @Test
    public void shouldFindByIdIfAlbumExists() {
        final User artist = new User(1L, "email", "name", AccountType.GOOGLE);
        final Album album = new Album(33L, "Some album", artist);
        Mockito.when(albumRepository.findById(33L)).thenReturn(Optional.of(album));

        final Album album1 = albumService.findById(33L);

        Assertions.assertNotNull(album1);
        Assertions.assertEquals(album, album1);
    }

    @Test
    public void shouldNotCreateAlbumIfUserNotExists() {
        final NotFoundException e = Assertions.assertThrows(NotFoundException.class, () -> albumService.create(new CreateAlbumDTO("Some album"), null));
        Assertions.assertEquals("404 User not found.", e.getMessage());
    }

    @Test
    public void shouldNotCreateAlbumIfBlankName() {
        final User artist = new User(1L, "email", "name", AccountType.GOOGLE);
        Assertions.assertThrows(ConstraintViolationException.class, () -> albumService.create(new CreateAlbumDTO(""), artist));
    }

    @Test
    public void shouldCreateAlbum() {
        final User artist = new User(1L, "email", "name", AccountType.GOOGLE);
        final Album fakeCreatedAlbum = new Album(1L, "Some album", artist);

        Mockito.when(albumRepository.save(Mockito.any())).thenReturn(fakeCreatedAlbum);

        final Album album = albumService.create(new CreateAlbumDTO("Some album"), artist);
        Assertions.assertEquals(fakeCreatedAlbum, album);
    }
}
