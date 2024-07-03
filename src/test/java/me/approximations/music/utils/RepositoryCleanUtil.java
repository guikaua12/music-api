package me.approximations.music.utils;

import lombok.RequiredArgsConstructor;
import me.approximations.music.repositories.AlbumRepository;
import me.approximations.music.repositories.SongRepository;
import me.approximations.music.repositories.UserRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public final class RepositoryCleanUtil {
    private final UserRepository userRepository;
    private final SongRepository songRepository;
    private final AlbumRepository albumRepository;

    
    public void clean() {
        songRepository.deleteAllInBatch();
        albumRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
    }
}
