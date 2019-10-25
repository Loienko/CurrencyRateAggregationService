package net.ukr.dreamsicle.exception;

/**
 * ResourceNotFoundException class that extends {@link RuntimeException}
 *
 * @author yurii.loienko
 * @version 1.0
 */
public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException() {
        super("Resource not found");
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
