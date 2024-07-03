package me.approximations.music.services.storage.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectResult;
import lombok.RequiredArgsConstructor;
import me.approximations.music.services.storage.StorageService;
import me.approximations.music.services.storage.result.upload.S3FileUploadResult;
import me.approximations.music.services.storage.resolver.StorageObjectUrlResolver;
import me.approximations.music.services.storage.strategies.FilenameGeneratorStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@RequiredArgsConstructor
@Service
@Profile("default")
public class S3StorageService implements StorageService<S3FileUploadResult> {
    private final AmazonS3 client;
    @Value("${cloudflare.r2.bucket}")
    private String bucket;
    private final FilenameGeneratorStrategy filenameGeneratorStrategy;
    private final StorageObjectUrlResolver storageObjectUrlResolver;

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public S3FileUploadResult upload(MultipartFile multipartFile) throws IOException {
        final File file = multipartToFile(multipartFile);
        final String newFileName = filenameGeneratorStrategy.generate(file.getName());

        final PutObjectResult result = client.putObject(bucket, newFileName, file);
        file.delete();

        return new S3FileUploadResult(result, multipartFile, newFileName);
    }

    @Override
    public boolean objectExists(String filename) {
        return client.getObject(bucket, filename) != null;
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
