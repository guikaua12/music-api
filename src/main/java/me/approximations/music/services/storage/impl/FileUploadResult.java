package me.approximations.music.services.storage.impl;

import com.amazonaws.services.s3.model.PutObjectResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
public class FileUploadResult {
    private final PutObjectResult putObjectResult;
    private final MultipartFile multipartFile;
    private final String filename;
}
