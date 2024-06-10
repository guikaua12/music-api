package me.approximations.music.services.storage;

import me.approximations.music.services.storage.result.upload.FileUploadResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StorageService<UR extends FileUploadResult> {
    UR upload(MultipartFile file) throws IOException;

    boolean objectExists(String filename);

}
