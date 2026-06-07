package com.Gourav.Banking_System.exceptions;

public class OverdraftLimitExceededException extends RuntimeException {

    public OverdraftLimitExceededException(String message) {
        super(message);
    }
}