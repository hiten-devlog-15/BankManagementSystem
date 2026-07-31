package com.hiten.bankmanagementsystem.exception;

public class BalanceNotZeroException extends BankException {
    public BalanceNotZeroException() {
        super("Balance is not zero so cant close account");
    }
}
