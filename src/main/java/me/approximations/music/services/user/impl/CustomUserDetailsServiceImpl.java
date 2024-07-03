package me.approximations.music.services.user.impl;

import lombok.RequiredArgsConstructor;
import me.approximations.music.dtos.input.CreateUserDTO;
import me.approximations.music.entities.User;
import me.approximations.music.entities.enums.AccountType;
import me.approximations.music.repositories.UserRepository;
import me.approximations.music.security.entities.CustomUserDetails;
import me.approximations.music.services.user.CustomUserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {
    private final UserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return findByEmail(email);
    }

    @Override
    public CustomUserDetails findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(CustomUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Email not found."));
    }

    @Override
    public User create(CreateUserDTO dto) {
        final User user = new User(null, dto.email(), dto.name(), AccountType.GOOGLE);

        return userRepository.save(user);
    }
}
