package me.approximations.music.services.album;

import jakarta.validation.Valid;
import me.approximations.music.dtos.input.CreateAlbumDTO;
import me.approximations.music.entities.Album;
import me.approximations.music.entities.User;

import java.util.Set;

public interface AlbumService {
    Album findById(Long id);

    Album create(@Valid CreateAlbumDTO dto, User user);

    Set<Album> getAllByUser(Long userId);
}
