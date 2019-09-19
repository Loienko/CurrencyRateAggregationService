package net.ukr.dreamsicle.exception;

public class ArgumentNotValidException extends RuntimeException {
    public ArgumentNotValidException() {
        super("Not valid Data");
    }
}