package com.hiten.bankmanagementsystem.exception;

public class SameAccountTransferException extends BankException{
    public SameAccountTransferException(){
        super("You are transferring in same account");
    }
}
