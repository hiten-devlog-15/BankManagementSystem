package com.hiten.bankmanagementsystem.exception;

public class InvalidAmountException extends BankException {
    public InvalidAmountException() {
        super("Invalid amount");
    }
}
