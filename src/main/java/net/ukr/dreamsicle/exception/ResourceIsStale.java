package net.ukr.dreamsicle.exception;

public class ResourceIsStale extends RuntimeException {
    public ResourceIsStale() {
        super("Data is Stale. Please Retry");
    }
}
