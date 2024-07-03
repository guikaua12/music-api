package me.approximations.music.dtos.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.web.multipart.MultipartFile;

public record UploadSongDTO(@NotBlank String name, @NotBlank String imageUrl,
                            @PositiveOrZero Long albumId, @NotNull MultipartFile file) {
}
