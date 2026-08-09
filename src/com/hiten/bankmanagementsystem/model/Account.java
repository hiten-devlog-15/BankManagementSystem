package com.hiten.bankmanagementsystem.model;

import com.hiten.bankmanagementsystem.enums.AccountStatus;
import com.hiten.bankmanagementsystem.enums.AccountType;

import java.time.LocalDate;

public class Account {
    private int accountId;
    private final Customer customer;
    private final AccountType accountType;
    private double currentBalance;
    private AccountStatus accountStatus;
    private final LocalDate createdAt;

    public Account(int accountId, Customer customer, AccountType accountType, double currentBalance, AccountStatus accountStatus, LocalDate createdAt){
        this.accountId = accountId;
        this.customer = customer;
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

    public double getCurrentBalance(){
        return currentBalance;
    }
    public AccountType getAccountType() {
        return accountType;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setAccountId(int accountId){
        this.accountId = accountId;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void deposit(double amount){
        currentBalance = currentBalance + amount;
    }

    public void withdraw(double amount){
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
