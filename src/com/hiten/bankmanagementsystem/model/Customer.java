package com.hiten.bankmanagementsystem.model;

import java.time.LocalDate;

public class Customer {
    private final int customerId;
    private final String name;
    private final String phoneNumber;
    private final String email;
    private final String password;
    private final LocalDate createdAt;

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

    public int getCustomerId(){
        return customerId;
    }
    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "Customer ID: " + customerId +
                "\nName: " + name +
                "\nEmail: " + email +
                "\nPhone Number: " + phoneNumber +
                "\nCreated At: " + createdAt +
                "\n----------------------------------";

    }
}
