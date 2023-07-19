package ru.cft.shift.intensive.template.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("api.user.delete.api-responses.404.description");
    }
}
