package com.hiten.bankmanagementsystem.exception;

public class AccountAlreadyExistsException extends BankException {
    public AccountAlreadyExistsException() {
        super("Account already exists");
    }
}
