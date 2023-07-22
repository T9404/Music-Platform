package ru.cft.shift.intensive.template.exception;

public class SignerAlreadyExistsException extends RuntimeException {
    public SignerAlreadyExistsException() {
        super("api.signer.create.api-responses.409.description");
    }
}
