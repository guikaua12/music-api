package me.approximations.music.services.storage.strategies.impl;

import me.approximations.music.services.storage.strategies.FilenameGeneratorStrategy;

public class NoOpFilenameGeneratorStrategy implements FilenameGeneratorStrategy {
    @Override
    public String generate(String name) {
        return name;
    }
}
