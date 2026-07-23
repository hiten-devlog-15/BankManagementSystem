package com.hiten.bankmanagementsystem.model;

import java.time.LocalDate;

public class Transaction {

    private int transactionId;
    private Account account;
    private String type;
    private int amount;
    private LocalDate date;
    private int balanceAfterTransaction;

    public Transaction(int transactionId, Account account, String type, int amount, LocalDate date, int currentBalance){
        this.transactionId = transactionId;
        this.account = account;
        this.type = type;
        this.amount = amount;
        this.date = date;
        this.balanceAfterTransaction = currentBalance;
    }
}
