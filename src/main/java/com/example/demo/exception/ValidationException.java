package com.example.demo.exception;

public class ValidationException extends RuntimeException {
    private final int errorCode;
    private final String errorDescription;

    public ValidationException(int errorCode, String errorDescription) {
        super(errorDescription);
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorDescription() {
        return errorDescription;
    }
}
