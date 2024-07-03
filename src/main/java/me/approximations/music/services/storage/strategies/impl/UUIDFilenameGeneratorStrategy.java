package me.approximations.music.services.storage.strategies.impl;

import me.approximations.music.services.storage.strategies.FilenameGeneratorStrategy;

import java.util.UUID;

public class UUIDFilenameGeneratorStrategy implements FilenameGeneratorStrategy {
    @Override
    public String generate(String name) {
        return String.format("%s-%s", name, UUID.randomUUID());
    }
}
