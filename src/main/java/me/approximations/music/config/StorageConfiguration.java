package me.approximations.music.config;

import lombok.RequiredArgsConstructor;
import me.approximations.music.properties.AwsProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;

@RequiredArgsConstructor
@Configuration
@Profile("default")
public class StorageConfiguration {
    @Bean
    public S3Client amazonS3(AwsProperties awsProperties) {
        final AwsCredentials awsCredentials = AwsBasicCredentials.builder()
                .accessKeyId(awsProperties.getAccessKey())
                .secretAccessKey(awsProperties.getSecretKey())
                .build();

        return S3Client.builder()
                .region(Region.of("auto"))
                .endpointOverride(URI.create(awsProperties.getEndpoint()))
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                .build();
    }
}
