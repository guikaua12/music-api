package me.approximations.music.services.storage;

import me.approximations.music.services.storage.impl.FileUploadResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StorageService {
    FileUploadResult upload(MultipartFile file) throws IOException;
}
