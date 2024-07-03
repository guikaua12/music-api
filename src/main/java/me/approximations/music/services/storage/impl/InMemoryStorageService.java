package me.approximations.music.services.storage.impl;

import lombok.RequiredArgsConstructor;
import me.approximations.music.services.storage.StorageService;
import me.approximations.music.services.storage.resolver.StorageObjectUrlResolver;
import me.approximations.music.services.storage.result.upload.StorageUploadResult;
import me.approximations.music.services.storage.strategies.FilenameGeneratorStrategy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
@Profile("test")
public class InMemoryStorageService implements StorageService {
    private final Map<String, MultipartFile> objects = new HashMap<>();
    private final FilenameGeneratorStrategy filenameGeneratorStrategy;
    private final StorageObjectUrlResolver storageObjectUrlResolver;

    @Override
    public StorageUploadResult upload(MultipartFile multipartFile) throws IOException {
        final String newFileName = filenameGeneratorStrategy.generate(multipartFile.getOriginalFilename());

        objects.put(newFileName, multipartFile);

        return new StorageUploadResult(multipartFile, newFileName, storageObjectUrlResolver.getObjectUrl(newFileName));
    }

    @Override
    public boolean objectExists(String filename) {
        return objects.get(filename) != null;
    }
}
