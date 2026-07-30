package com.hiten.bankmanagementsystem.exception;

public class InvalidPasswordException extends BankException {
    public InvalidPasswordException() {
        super("Invalid password");
    }
}
