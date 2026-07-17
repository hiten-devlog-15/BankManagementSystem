package com.hiten.bankmanagementsystem.model;

import java.time.LocalDate;

public class Customer {
    private int customerId;
    private String name;
    private String phoneNumber;
    private String email;
    private String password;
    private LocalDate createdAt;

    public Customer(int customerId, String name, String phoneNumber, String email, String password, LocalDate createdAt){
        this.customerId = customerId;
        this.name=name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.createdAt = createdAt;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }

}
