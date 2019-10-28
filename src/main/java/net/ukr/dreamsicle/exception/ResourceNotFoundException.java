package net.ukr.dreamsicle.exception;

import net.ukr.dreamsicle.util.Constants;

/**
 * ResourceNotFoundException class that extends {@link RuntimeException}
 *
 * @author yurii.loienko
 * @version 1.0
 */
public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException() {
        super(Constants.RESOURCE_NOT_FOUND);
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
