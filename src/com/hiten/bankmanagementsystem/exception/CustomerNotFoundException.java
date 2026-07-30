package com.hiten.bankmanagementsystem.exception;

public class CustomerNotFoundException extends BankException {
    public CustomerNotFoundException() {
        super("Customer Not Found");
    }
}
