package ru.cft.shift.intensive.template.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record SignerDto(@NotEmpty @Size(min = 3, max = 50) String name,
                        @Email String email,
                        String biography,
                        Set<AlbumDto> albums) {
}
