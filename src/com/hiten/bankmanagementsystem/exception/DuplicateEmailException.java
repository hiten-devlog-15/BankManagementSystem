package com.hiten.bankmanagementsystem.exception;

public class DuplicateEmailException extends BankException{
    public DuplicateEmailException(){
        super("Email already exists");
    }
}
