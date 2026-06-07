package com.Gourav.Banking_System.exceptions;

public class SelfTransferException extends RuntimeException {

    public SelfTransferException(String message) {
        super(message);
    }
}