package com.recrutement.exceptions;

public class DeactivatedAccountException extends Exception {
    public DeactivatedAccountException() {}
    public DeactivatedAccountException(String message) {
        super(message);
    }
}
