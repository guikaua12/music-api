package me.approximations.music.dtos;

import jakarta.validation.constraints.NotBlank;

public record CreateAlbumDTO(@NotBlank String name) {
}
