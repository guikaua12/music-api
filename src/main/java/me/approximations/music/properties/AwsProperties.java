package me.approximations.music.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix="aws")
public class AwsProperties {
    private String accessKey;
    private String secretKey;
    private String endpoint;
    private String bucket;
    private String publicUrl;
}
