package me.approximations.music.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public final class CloudflareObjectUrlResolverUtils {
    private static String bucketPublicUrl;

    public static String getObjectUrl(String filename) {
        return bucketPublicUrl + "/" + filename;
    }

    @Value("${cloudflare.r2.public_url}")
    public void setBucketPublicUrl(String url) {
        bucketPublicUrl = url;
    }
}
