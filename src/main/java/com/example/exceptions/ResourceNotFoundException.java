package com.example.exceptions;

public class ResourceNotFoundException extends Exception {
    public ResourceNotFoundException(String msg) {
        super(msg);
    }
}
