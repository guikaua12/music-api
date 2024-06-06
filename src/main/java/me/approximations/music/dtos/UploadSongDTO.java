package me.approximations.music.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record UploadSongDTO(@NotBlank String name, @NotBlank String imageUrl,
                            @NotEmpty Long albumId, @NotNull MultipartFile file) {
}
