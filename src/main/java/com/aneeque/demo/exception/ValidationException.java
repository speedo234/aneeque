package com.aneeque.demo.exception;

public class ValidationException extends ApplicationException {
    public ValidationException() {
        super("Invalid!");
    }

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationException(Throwable cause) {
        super(cause);
    }
}
