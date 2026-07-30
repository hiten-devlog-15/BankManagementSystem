package com.hiten.bankmanagementsystem.exception;

public class InvalidEmailException extends BankException {
    public InvalidEmailException() {
        super("Invalid email");
    }
}
