package ru.cft.shift.intensive.template.exception;

public class SongNotFoundException extends RuntimeException {
    public SongNotFoundException() {
        super("api.song.api-responses.404.description");
    }
}
