package ru.namazov.ibetyouwill.exceptions;

import ru.namazov.ibetyouwill.base.exception.BaseException;

public class JwtAuthenticationException extends BaseException {

    public JwtAuthenticationException(String message) {
        super(message);
    }

    public JwtAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

    public JwtAuthenticationException(Throwable cause) {
        super(cause);
    }

    public JwtAuthenticationException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
