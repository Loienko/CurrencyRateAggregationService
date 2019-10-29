package net.ukr.dreamsicle.exception;

/**
 * CustomDataAlreadyExistsException class that extends {@link RuntimeException}
 *
 * @author yurii.loienko
 * @version 1.0
 */
public class CustomDataAlreadyExistsException extends RuntimeException {
    public CustomDataAlreadyExistsException(String message) {
        super(message);
    }
}
