package me.approximations.music.services.user.impl;

import lombok.RequiredArgsConstructor;
import me.approximations.music.dtos.CreateUserDTO;
import me.approximations.music.entities.User;
import me.approximations.music.entities.enums.AccountType;
import me.approximations.music.repositories.UserRepository;
import me.approximations.music.services.user.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User create(CreateUserDTO dto) {
        final User user = new User(null, dto.email(), dto.name(), AccountType.GOOGLE);

        return userRepository.save(user);
    }
}
