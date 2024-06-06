package me.approximations.music.services.storage.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectResult;
import lombok.RequiredArgsConstructor;
import me.approximations.music.services.storage.StorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class StorageServiceImpl implements StorageService {
    private final AmazonS3 client;
    @Value("${cloudflare.r2.bucket}")
    private String bucket;

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public FileUploadResult upload(MultipartFile multipartFile) throws IOException {
        final File file = multipartToFile(multipartFile);
        final String newFileName = String.format("%s-%s", file.getName(), UUID.randomUUID().toString());

        final PutObjectResult result = client.putObject(bucket, newFileName, file);
        file.delete();

        return new FileUploadResult(result, multipartFile, newFileName);
    }

    private File multipartToFile(MultipartFile multipartFile) {
        final File file = new File(multipartFile.getOriginalFilename());

        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(multipartFile.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return file;
    }
}
