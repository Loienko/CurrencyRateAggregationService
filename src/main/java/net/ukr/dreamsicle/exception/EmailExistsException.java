package net.ukr.dreamsicle.exception;

public class EmailExistsException extends RuntimeException {

    public EmailExistsException(final String message) {
        super(message);
    }
}