package ru.cft.shift.intensive.template.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UserDto(@NotEmpty @Size(min = 3, max = 50) String username,
                      @NotEmpty @Size(min = 3, max = 50) String password,
                      @NotEmpty @Email String email,
                      @NotEmpty String role) {
}
