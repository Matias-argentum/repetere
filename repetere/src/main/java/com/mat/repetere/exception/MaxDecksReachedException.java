package com.mat.repetere.exception;

public class MaxDecksReachedException extends RuntimeException {
    public MaxDecksReachedException(String message) {
        super(message);
    }
}
