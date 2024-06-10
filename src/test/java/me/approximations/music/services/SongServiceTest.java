package me.approximations.music.services;

import me.approximations.music.dtos.UploadSongDTO;
import me.approximations.music.entities.Album;
import me.approximations.music.entities.Song;
import me.approximations.music.entities.User;
import me.approximations.music.entities.enums.AccountType;
import me.approximations.music.exceptions.NotFoundException;
import me.approximations.music.exceptions.UnsupportedFileTypeException;
import me.approximations.music.repositories.AlbumRepository;
import me.approximations.music.repositories.SongRepository;
import me.approximations.music.services.song.SongService;
import me.approximations.music.services.storage.StorageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.MimeTypeUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@SpringBootTest
public class SongServiceTest {
    private static final String projectDir = System.getProperty("user.dir");
    @Autowired
    private SongService songService;
    @Autowired
    private StorageService<?> storageService;
    @MockBean
    private AlbumRepository albumRepository;
    @MockBean
    private SongRepository songRepository;

    private MockMultipartFile validFile;

    @BeforeEach
    public void beforeEach() throws IOException {
        final File file = new File(Path.of(projectDir, "src\\test\\java\\me\\approximations\\music\\services\\valid_file.mp3").toString());
        this.validFile = new MockMultipartFile("valid_file.mp3", "valid_file.mp3", Files.probeContentType(file.toPath()),
                new FileInputStream(file)
        );
    }

    @Test
    public void shouldNotUploadSongIfFileIsNotMusic() throws IOException {
        final File file = new File(Path.of(projectDir, "src\\test\\java\\me\\approximations\\music\\services\\invalid_file.png").toString());
        final MockMultipartFile multipartFile = new MockMultipartFile("invalid_file", "invalid_file", MimeTypeUtils.IMAGE_PNG_VALUE,
                new FileInputStream(file)
        );

        final UploadSongDTO dto = new UploadSongDTO("name", "image", 1L, multipartFile);

        Assertions.assertThrows(UnsupportedFileTypeException.class, () -> songService.uploadSong(dto));
    }

    @Test
    public void shouldNotUploadSongIfAlbumNotFound() {
        Mockito.when(albumRepository.findById(1L)).thenReturn(Optional.empty());

        final UploadSongDTO dto = new UploadSongDTO("name", "image", 1L, validFile);

        Assertions.assertThrows(NotFoundException.class, () -> songService.uploadSong(dto));
    }

    @Test
    public void shouldUploadSong() {
        final User artistMock = new User(1L, "email", "name", AccountType.GOOGLE);
        final Album mockAlbum = new Album(1L, "Album1", artistMock);
        final Song mockSong = new Song(1L, "name", "image", "valid_file.mp3", mockAlbum, null);

        Mockito.when(albumRepository.findById(1L)).thenReturn(Optional.of(mockAlbum));
        Mockito.when(songRepository.findById(1L)).thenReturn(Optional.of(mockSong));

        final UploadSongDTO dto = new UploadSongDTO("name", "image", 1L, validFile);

        final Song song = songService.uploadSong(dto);

        Assertions.assertEquals("valid_file.mp3", song.getFilename());
        Assertions.assertEquals("name", song.getName());
        Assertions.assertEquals(mockAlbum, song.getAlbum());

        Assertions.assertTrue(storageService.objectExists("valid_file.mp3"));
    }
}
