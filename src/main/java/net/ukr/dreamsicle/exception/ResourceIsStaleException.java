package net.ukr.dreamsicle.exception;

/**
 * ResourceIsStaleException class that extends {@link RuntimeException}
 *
 * @author yurii.loienko
 * @version 1.0
 * @deprecated
 */
@Deprecated
public class ResourceIsStaleException extends RuntimeException {
    public ResourceIsStaleException() {
        super("Data is Stale. Please Retry");
    }
}