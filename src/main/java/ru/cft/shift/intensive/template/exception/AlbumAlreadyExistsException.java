package ru.cft.shift.intensive.template.exception;

public class AlbumAlreadyExistsException extends RuntimeException {
    public AlbumAlreadyExistsException() {
        super("api.album.api-responses.409.description");
    }
}
