package dressshop.exception;

public abstract class DressshopException extends RuntimeException {

    public DressshopException(String message) {
        super(message);
    }

    public abstract int StatusCode();
}
