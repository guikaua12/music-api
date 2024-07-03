package me.approximations.music.dtos.input;

import jakarta.validation.constraints.NotBlank;

public record CreateAlbumDTO(@NotBlank String name) {
}
