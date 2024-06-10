package me.approximations.music.services.storage.strategies.impl;

import me.approximations.music.services.storage.strategies.FilenameGeneratorStrategy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Profile("default")
public class UUIDFilenameGeneratorStrategy implements FilenameGeneratorStrategy {
    @Override
    public String generate(String name) {
        return String.format("%s-%s", name, UUID.randomUUID());
    }
}
