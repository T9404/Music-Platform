package ru.cft.shift.intensive.template.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record SongDto(@NotEmpty @Size(min = 1, max = 50) String name,
                      @NotEmpty int duration,
                      String description) {
}
