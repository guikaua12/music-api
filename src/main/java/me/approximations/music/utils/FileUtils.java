package me.approximations.music.utils;

import org.springframework.web.multipart.MultipartFile;

public final class FileUtils {
    public static boolean isAudioFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("audio/");
    }
}
