package me.approximations.music.services.storage.resolver.impl;

import me.approximations.music.services.storage.resolver.StorageObjectUrlResolver;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("test")
public class InMemoryStorageObjectUrlResolver implements StorageObjectUrlResolver {
    @Override
    public String getObjectUrl(String filename) {
        return "http://localhost:3333/" + filename;
    }
}
