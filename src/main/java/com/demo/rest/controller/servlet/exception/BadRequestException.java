package com.demo.rest.controller.servlet.exception;

public class BadRequestException extends HttpRequestException {
    private static final int RESPONSE_CODE = 400;

    public BadRequestException() {
        super(400);
    }

    public BadRequestException(String message) {
        super(message, 400);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause, 400);
    }

    public BadRequestException(Throwable cause) {
        super(cause, 400);
    }

    public BadRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace, 400);
    }
}
