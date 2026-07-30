package com.hiten.bankmanagementsystem.exception;

public class InvalidPhoneNumberException extends BankException{
    public InvalidPhoneNumberException(){
        super("Invalid phone number");
    }
}
