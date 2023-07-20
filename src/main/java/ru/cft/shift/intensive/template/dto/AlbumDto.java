package ru.cft.shift.intensive.template.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.sql.Timestamp;
import java.util.Set;

public record AlbumDto(@NotEmpty @Size(min = 1, max = 50) String albumName,
                       @NotEmpty String releaseDate,
                       @NotEmpty @Size(min = 3, max = 50) String signerName,
                       @NotEmpty String genre,
                       Set<SongDto> songs) {
}
