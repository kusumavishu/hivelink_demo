package com.aits.hivelink_demo.exception;

import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException{
    private final HttpStatus status;
    private final Object data;

    public ApiException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.data = null;
    }

    public ApiException(HttpStatus status, String message, Object data) {
        super(message);
        this.status = status;
        this.data = data;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public Object getData() {
        return data;
    }
}
