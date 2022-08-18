package com.revature.exotic_jerky.utils.custom_exceptions;

public class InvalidCartException extends RuntimeException{
    public InvalidCartException() {
    }

    public InvalidCartException(String message) {
        super(message);
    }

    public InvalidCartException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidCartException(Throwable cause) {
        super(cause);
    }

    public InvalidCartException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
