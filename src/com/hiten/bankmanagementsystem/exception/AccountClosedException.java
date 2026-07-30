package com.hiten.bankmanagementsystem.exception;

public class AccountClosedException extends BankException {
    public AccountClosedException() {
        super("Account is closed");
    }
}
