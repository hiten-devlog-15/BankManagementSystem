package com.hiten.bankmanagementsystem.model;
import java.time.LocalDate;

import com.hiten.bankmanagementsystem.enums.TransactionType;

public class Transaction {

    private int transactionId;
    private final Account account;
    private final TransactionType transactionType;
    private final double amount;
    private final LocalDate date;
    private final double balanceAfterTransaction;

    public int getTransactionId() {
        return transactionId;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getBalanceAfterTransaction() {
        return balanceAfterTransaction;
    }

    public void setTransactionId(int transactionId){
        this.transactionId = transactionId;
    }



    public Transaction(int transactionId, Account account, TransactionType type, double amount, LocalDate date, double currentBalance){
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
