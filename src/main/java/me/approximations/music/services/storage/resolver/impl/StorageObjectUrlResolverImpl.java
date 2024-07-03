package me.approximations.music.services.storage.resolver.impl;

import lombok.RequiredArgsConstructor;
import me.approximations.music.properties.AwsProperties;
import me.approximations.music.services.storage.resolver.StorageObjectUrlResolver;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("default")
@RequiredArgsConstructor
public class StorageObjectUrlResolverImpl implements StorageObjectUrlResolver {
    private final AwsProperties awsProperties;

    @Override
    public String getObjectUrl(String filename) {
        return awsProperties.getPublicUrl() + "/" + filename;
    }
}
