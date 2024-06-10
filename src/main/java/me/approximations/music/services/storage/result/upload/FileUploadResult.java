package me.approximations.music.services.storage.result.upload;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadResult {
    MultipartFile getMultipartFile();

    String getFilename();
}
