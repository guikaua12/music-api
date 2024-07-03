package me.approximations.music.services.storage.impl;

import lombok.RequiredArgsConstructor;
import me.approximations.music.properties.AwsProperties;
import me.approximations.music.services.storage.StorageService;
import me.approximations.music.services.storage.result.upload.S3FileUploadResult;
import me.approximations.music.services.storage.resolver.StorageObjectUrlResolver;
import me.approximations.music.services.storage.strategies.FilenameGeneratorStrategy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@RequiredArgsConstructor
@Service
@Profile("default")
public class S3StorageService implements StorageService {
    private final S3Client client;
    private final AwsProperties awsProperties;
    private final FilenameGeneratorStrategy filenameGeneratorStrategy;
    private final StorageObjectUrlResolver storageObjectUrlResolver;

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public S3FileUploadResult upload(MultipartFile multipartFile) throws IOException {
        final File file = multipartToFile(multipartFile);
        final String newFileName = filenameGeneratorStrategy.generate(file.getName());

        final PutObjectRequest request = PutObjectRequest.builder()
                .bucket(awsProperties.getBucket())
                .key(newFileName)
                .build();

        client.putObject(request, RequestBody.fromFile(file));
        file.delete();

        return new S3FileUploadResult(result, multipartFile, newFileName);
    }

    @Override
    public boolean objectExists(String filename) {
        return client.headObject(b -> b.bucket(awsProperties.getBucket()).key(filename)) != null;
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
