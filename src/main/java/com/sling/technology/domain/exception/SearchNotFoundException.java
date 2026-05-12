package com.sling.technology.domain.exception;

public class SearchNotFoundException extends RuntimeException {
    public SearchNotFoundException(String message) {
        super(message);
    }
}
