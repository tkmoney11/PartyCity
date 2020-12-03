package com.tyler.api.exceptions;

public class SameHostException extends Exception {
    public SameHostException() {
    }

    public SameHostException(String message) {
        super(message);
    }

    public SameHostException(String message, Throwable cause) {
        super(message, cause);
    }

    public SameHostException(Throwable cause) {
        super(cause);
    }

    public SameHostException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
