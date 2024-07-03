package me.approximations.music.services.storage;

import me.approximations.music.services.storage.result.upload.StorageUploadResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StorageService {
    StorageUploadResult upload(MultipartFile file) throws IOException;

    boolean objectExists(String filename);

}
