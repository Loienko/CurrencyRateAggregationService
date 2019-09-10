package net.ukr.dreamsicle.exception;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException() {
        super("Currency not found");
    }
}
