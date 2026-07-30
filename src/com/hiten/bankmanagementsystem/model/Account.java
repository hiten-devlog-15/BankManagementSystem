package com.hiten.bankmanagementsystem.model;

import com.hiten.bankmanagementsystem.enums.AccountStatus;

import java.time.LocalDate;

public class Account {

    private Customer customer;
    private int accountId;
    private String accountType;
    private int currentBalance;



    private AccountStatus accountStatus;
    private LocalDate createdAt;


    public Account(Customer customer, int accountId, String accountType, int currentBalance, AccountStatus accountStatus, LocalDate createdAt){
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

    public void depositAmount(int amount){
        currentBalance = currentBalance + amount;
    }

    public void withdrawAmount(int amount){
        currentBalance = currentBalance - amount;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
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
