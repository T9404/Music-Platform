package ru.cft.shift.intensive.template.exception;

public class SingerAlreadyExistsException extends RuntimeException {
    public SingerAlreadyExistsException() {
        super("api.singer.create.api-responses.409.description");
    }
}
