package com.hiten.bankmanagementsystem.exception;

public class DuplicatePhoneNumberException extends BankException {
    public DuplicatePhoneNumberException(){
        super("Phone number already exists");
    }
}
