package me.approximations.music.dtos.input;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateUserDTO(@Email String email, @NotBlank String name) {
}
