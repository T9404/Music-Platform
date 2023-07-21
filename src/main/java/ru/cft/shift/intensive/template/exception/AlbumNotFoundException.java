package ru.cft.shift.intensive.template.exception;

public class AlbumNotFoundException extends RuntimeException {
    public AlbumNotFoundException() {
        super("api.album.api-responses.404.description");
    }
}
