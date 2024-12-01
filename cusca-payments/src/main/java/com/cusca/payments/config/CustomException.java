package com.cusca.payments.config;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final String message;

    public CustomException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

