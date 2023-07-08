package ru.cft.shift.intensive.template.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record UserDto(@NotEmpty @Size(min = 3, max = 50) String username,
                      @NotEmpty @Size(min = 3, max = 50) String password,
                      @NotEmpty Set<String> roles) {
}
