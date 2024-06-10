package me.approximations.music.services.storage.result.upload;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@RequiredArgsConstructor
public class InMemoryFileUploadResult implements FileUploadResult {
    private final MultipartFile multipartFile;
    private final String filename;
}
