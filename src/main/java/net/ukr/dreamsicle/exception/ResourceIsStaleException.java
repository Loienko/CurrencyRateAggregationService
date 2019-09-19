package net.ukr.dreamsicle.exception;

public class ResourceIsStaleException extends RuntimeException{
    public ResourceIsStaleException() {
        super("Data is Stale. Please Retry");
    }
}