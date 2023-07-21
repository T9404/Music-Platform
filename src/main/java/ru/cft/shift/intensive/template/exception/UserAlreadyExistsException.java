package ru.cft.shift.intensive.template.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException() {
        super("api.user.api-responses.409.description");
    }
}
