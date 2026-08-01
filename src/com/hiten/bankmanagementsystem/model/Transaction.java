package com.hiten.bankmanagementsystem.model;
import java.time.LocalDate;

import com.hiten.bankmanagementsystem.enums.TransactionType;

public class Transaction {

    private final int transactionId;
    private final Account account;
    private final TransactionType transactionType;
    private final int amount;
    private final LocalDate date;

    public int getTransactionId() {
        return transactionId;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public int getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getBalanceAfterTransaction() {
        return balanceAfterTransaction;
    }

    private final int balanceAfterTransaction;

    public Transaction(int transactionId, Account account, TransactionType type, int amount, LocalDate date, int currentBalance){
        this.transactionId = transactionId;
        this.account = account;
        this.transactionType = type;
        this.amount = amount;
        this.date = date;
        this.balanceAfterTransaction = currentBalance;
    }

    public Account getAccount() {
        return account;
    }

    @Override
    public String toString(){
        return  "TransactionID: " + transactionId +
                "\nAccountID: " + account.getAccountId() +
                "\nTransaction Type: " + transactionType +
                "\nAmount: " + amount +
                "\nDate: "+ date +
                "\nCurrent Balance: " + balanceAfterTransaction +
                "\n----------------------------------";
    }


}
