package com.hiten.bankmanagementsystem.model;

import com.hiten.bankmanagementsystem.enums.AccountStatus;
import com.hiten.bankmanagementsystem.enums.AccountType;

import java.time.LocalDate;

public class Account {
    private final int accountId;
    private final Customer customer;
    private final AccountType accountType;
    private int currentBalance;
    private AccountStatus accountStatus;
    private final LocalDate createdAt;


    public Account(Customer customer, int accountId, AccountType accountType, int currentBalance, AccountStatus accountStatus, LocalDate createdAt){
        this.customer = customer;
        this.accountId = accountId;
        this.accountType = accountType;
        this.currentBalance = currentBalance;
        this.accountStatus = accountStatus;
        this.createdAt = createdAt;
    }

    public Customer getCustomer(){
        return customer;
    }

    public int getAccountId(){
        return accountId;
    }

    public int getCurrentBalance(){
        return currentBalance;
    }
    public AccountType getAccountType() {
        return accountType;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }
    public void deposit(int amount){
        currentBalance = currentBalance + amount;
    }

    public void withdraw(int amount){
        currentBalance = currentBalance - amount;
    }



    public void closeAccount() {
        this.accountStatus = AccountStatus.CLOSED;
    }

    @Override
    public String toString(){
        return "Customer ID: " + customer.getCustomerId() +
                "\nAccountID: " + accountId +
                "\nAccount Type: " + accountType +
                "\nCurrent Balance: " + currentBalance +
                "\nAccount Status: " + accountStatus +
                "\nCreated At: "+ createdAt +
                "\n----------------------------------";
    }


}
