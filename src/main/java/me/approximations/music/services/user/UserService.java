package me.approximations.music.services.user;

import me.approximations.music.dtos.CreateUserDTO;
import me.approximations.music.entities.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByEmail(String email);

    User create(CreateUserDTO dto);
}
