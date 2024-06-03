package me.approximations.music.security.jwt;

import com.auth0.jwt.interfaces.DecodedJWT;
import me.approximations.music.entities.User;

public interface JwtService {
    String encode(User user);

    DecodedJWT decode(String token);
}
