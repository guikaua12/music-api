package me.approximations.music.repositories;

import me.approximations.music.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.albums a LEFT JOIN FETCH a.artist LEFT JOIN FETCH a.songs")
    Optional<User> findByEmail(String email);
}
