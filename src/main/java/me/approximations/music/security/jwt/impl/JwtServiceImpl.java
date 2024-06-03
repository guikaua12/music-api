package me.approximations.music.security.jwt.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import me.approximations.music.entities.User;
import me.approximations.music.security.jwt.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class JwtServiceImpl implements JwtService {
    private static final String ISSUER = "music";
    private final Algorithm algorithm;

    public JwtServiceImpl(@Value("${spring.security.jwt.secret}") String secret) {
        this.algorithm = Algorithm.HMAC256(secret);
    }

    @Override
    public String encode(User user) {
        return JWT.create()
                .withIssuer(ISSUER)
                .withClaim("email", user.getEmail())
                .withIssuedAt(Instant.now())
                .withSubject(String.valueOf(user.getId()))
                .sign(algorithm);
    }

    @Override
    public DecodedJWT decode(String token) {
        return JWT.require(algorithm)
                .withIssuer(ISSUER)
                .build()
                .verify(token);
    }
}
