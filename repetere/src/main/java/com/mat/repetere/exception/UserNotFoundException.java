package com.mat.repetere.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        String msg = "User with id: " + id + " not found";
        super(msg);
    }
}
