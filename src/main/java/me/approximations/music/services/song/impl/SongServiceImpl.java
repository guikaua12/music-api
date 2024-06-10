package me.approximations.music.services.song.impl;

import lombok.RequiredArgsConstructor;
import me.approximations.music.dtos.UploadSongDTO;
import me.approximations.music.entities.Album;
import me.approximations.music.entities.Song;
import me.approximations.music.exceptions.NotFoundException;
import me.approximations.music.exceptions.UnsupportedFileTypeException;
import me.approximations.music.repositories.AlbumRepository;
import me.approximations.music.repositories.SongRepository;
import me.approximations.music.services.song.SongService;
import me.approximations.music.services.storage.StorageService;
import me.approximations.music.services.storage.result.upload.FileUploadResult;
import me.approximations.music.utils.FileUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class SongServiceImpl implements SongService {
    private final AlbumRepository albumRepository;
    private final SongRepository songRepository;
    private final StorageService<?> storageService;

    @Transactional
    @Override
    public Song uploadSong(UploadSongDTO dto) {
        if (!FileUtils.isAudioFile(dto.file())) {
            throw new UnsupportedFileTypeException("Unsupported media type.");
        }

        final Album album = albumRepository.findById(dto.albumId()).orElseThrow(() -> new NotFoundException("Album not found."));

        final Song song = new Song(null, dto.name(), dto.imageUrl(), null, album, null);
        album.addSong(song);

        songRepository.save(song);

        try {
            final FileUploadResult result = storageService.upload(dto.file());

            song.setFilename(result.getFilename());
            song.updateSongUrl();
            return song;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Page<Song> searchByName(String name, Pageable pageable) {
        return songRepository.searchAllByName(name, pageable);
    }
}
