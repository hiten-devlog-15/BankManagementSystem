package com.hiten.bankmanagementsystem.exception;

public class InsufficientBalanceException extends BankException{
    public InsufficientBalanceException(){
        super("Insufficient balance");
    }
}
