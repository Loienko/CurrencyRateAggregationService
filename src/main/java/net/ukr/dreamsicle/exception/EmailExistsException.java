package net.ukr.dreamsicle.exception;

/**
 * EmailExistsException class that extends {@link RuntimeException}
 *
 * @author yurii.loienko
 * @version 1.0
 */
public class EmailExistsException extends RuntimeException {

    public EmailExistsException(final String message) {
        super(message);
    }
}