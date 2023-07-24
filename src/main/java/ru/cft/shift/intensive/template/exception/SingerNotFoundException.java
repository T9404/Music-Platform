package ru.cft.shift.intensive.template.exception;

public class SingerNotFoundException extends RuntimeException {
    public SingerNotFoundException() {
        super("api.singer.get.api-responses.404.description");
    }
}
