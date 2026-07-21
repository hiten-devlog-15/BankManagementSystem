package com.hiten.bankmanagementsystem.model;

import java.time.LocalDate;

public class Account {

    private Customer customer;
    private int accountId;
    private String accountType;
    private int currentBalance;
    private String accountStatus;
    private LocalDate createdAt;

    public Account(Customer customer, int accountId, String accountType, int currentBalance, String accountStatus, LocalDate createdAt){
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





}
