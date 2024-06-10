package me.approximations.music.services.storage.strategies.impl;

import me.approximations.music.services.storage.strategies.FilenameGeneratorStrategy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("test")
public class NoOpFilenameGeneratorStrategy implements FilenameGeneratorStrategy {
    @Override
    public String generate(String name) {
        return name;
    }
}
