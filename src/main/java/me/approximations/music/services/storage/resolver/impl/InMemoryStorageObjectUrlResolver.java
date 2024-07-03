package me.approximations.music.services.storage.resolver.impl;

import me.approximations.music.services.storage.resolver.StorageObjectUrlResolver;

public class InMemoryStorageObjectUrlResolver implements StorageObjectUrlResolver {
    @Override
    public String getObjectUrl(String filename) {
        return "http://localhost:3333/" + filename;
    }
}
