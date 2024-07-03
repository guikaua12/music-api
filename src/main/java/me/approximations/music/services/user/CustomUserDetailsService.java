package me.approximations.music.services.user;

import me.approximations.music.dtos.input.CreateUserDTO;
import me.approximations.music.entities.User;
import me.approximations.music.security.entities.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface CustomUserDetailsService extends UserDetailsService {
    CustomUserDetails findByEmail(String email) throws UsernameNotFoundException;

    User create(CreateUserDTO dto);
}
