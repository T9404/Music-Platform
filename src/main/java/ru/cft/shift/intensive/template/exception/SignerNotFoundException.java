package ru.cft.shift.intensive.template.exception;

public class SignerNotFoundException extends RuntimeException {
    public SignerNotFoundException() {
        super("api.signer.get.api-responses.404.description");
    }
}
