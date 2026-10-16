package com.mat.repetere.exception;

public class UnauthorizedRequest extends RuntimeException {
    public UnauthorizedRequest(String message) {
        super(message);
    }
}
