package com.aneeque.demo.exception;


public class AuthenticationException extends ApplicationException {
    public AuthenticationException() {
        super("Invalid!");
    }

    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthenticationException(Throwable cause) {
        super(cause);
    }
}
