package com.hiten.bankmanagementsystem.exception;

public class AccountNotFoundException extends BankException{
    public AccountNotFoundException(){
        super("Account not found");
    }
}
