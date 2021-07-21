package com.aneeque.demo.exception;


public class AuthorizationException extends ApplicationException {
    public AuthorizationException() {
        super("Invalid!");
    }

    public AuthorizationException(String message) {
        super(message);
    }

    public AuthorizationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthorizationException(Throwable cause) {
        super(cause);
    }
}
