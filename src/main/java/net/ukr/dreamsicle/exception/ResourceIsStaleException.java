package net.ukr.dreamsicle.exception;

/**
 * @deprecated
 */
@Deprecated
public class ResourceIsStaleException extends RuntimeException {
    public ResourceIsStaleException() {
        super("Data is Stale. Please Retry");
    }
}