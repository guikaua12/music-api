package me.approximations.music.services.storage.result.upload;

import com.amazonaws.services.s3.model.PutObjectResult;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@RequiredArgsConstructor
public class S3FileUploadResult implements FileUploadResult {
    private final PutObjectResult putObjectResult;
    private final MultipartFile multipartFile;
    private final String filename;
}
