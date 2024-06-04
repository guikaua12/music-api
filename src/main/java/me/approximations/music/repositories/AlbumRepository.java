package me.approximations.music.repositories;

import me.approximations.music.entities.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface AlbumRepository extends JpaRepository<Album, Long> {
    @SuppressWarnings("NullableProblems")
    @Query("SELECT a FROM Album a LEFT JOIN FETCH a.artist LEFT JOIN FETCH a.songs WHERE a.id = :id")
    Optional<Album> findById(Long id);

    @Query("SELECT a FROM Album a LEFT JOIN FETCH a.artist art WHERE art.id = :userId")
    Set<Album> getAllByUser(Long userId);
}
