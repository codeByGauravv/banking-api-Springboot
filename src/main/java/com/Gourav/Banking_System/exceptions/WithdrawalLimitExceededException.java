package com.Gourav.Banking_System.exceptions;

public class WithdrawalLimitExceededException extends RuntimeException {

    public WithdrawalLimitExceededException(String message) {
        super(message);
    }
}
