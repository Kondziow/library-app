package com.demo.rest.controller.servlet.exception;

public class NotFoundException extends HttpRequestException {
    private static final int RESPONSE_CODE = 404;

    public NotFoundException() {
        super(404);
    }

    public NotFoundException(String message) {
        super(message, 404);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause, 404);
    }

    public NotFoundException(Throwable cause) {
        super(cause, 404);
    }

    public NotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace, 404);
    }
}