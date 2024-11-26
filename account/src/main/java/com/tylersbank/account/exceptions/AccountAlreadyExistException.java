package com.tylersbank.account.exceptions;

public class AccountAlreadyExistException extends RuntimeException {
    public AccountAlreadyExistException(String message) {
        super(message);
    }
}
