package me.approximations.music.repositories;

import me.approximations.music.entities.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SongRepository extends JpaRepository<Song, Long> {
    @Query(value="SELECT s FROM Song s LEFT JOIN FETCH s.album WHERE s.name LIKE %:name%",
            countQuery="SELECT COUNT(s) FROM Song s"
    )
    Page<Song> searchAllByName(String name, Pageable pageable);

}
