package me.approximations.music.services.album.impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.approximations.music.dtos.input.CreateAlbumDTO;
import me.approximations.music.entities.Album;
import me.approximations.music.entities.User;
import me.approximations.music.exceptions.NotFoundException;
import me.approximations.music.repositories.AlbumRepository;
import me.approximations.music.repositories.UserRepository;
import me.approximations.music.services.album.AlbumService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Set;

@RequiredArgsConstructor
@Validated
@Service
public class AlbumServiceImpl implements AlbumService {
    private final UserRepository userRepository;
    private final AlbumRepository albumRepository;

    @Override
    public Album findById(Long id) {
        return albumRepository.findById(id).orElseThrow(() -> new NotFoundException("Album not found."));
    }

    @Override
    public Album create(@Valid CreateAlbumDTO dto, User user) {
        if (user == null) {
            throw new NotFoundException("User not found.");
        }

        final Album album = new Album(null, dto.name(), user);

        return albumRepository.save(album);
    }

    @Override
    public Set<Album> getAllByUser(Long userId) {
        userRepository.findSimpleById(userId).orElseThrow(() -> new NotFoundException("User not found."));

        return albumRepository.getAllByUser(userId);
    }
}
