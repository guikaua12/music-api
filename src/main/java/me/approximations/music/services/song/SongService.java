package me.approximations.music.services.song;

import me.approximations.music.dtos.input.UploadSongDTO;
import me.approximations.music.entities.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SongService {
    Song uploadSong(UploadSongDTO dto);

    Page<Song> searchByName(String name, Pageable pageable);
}
