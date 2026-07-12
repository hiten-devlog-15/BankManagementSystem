package com.hiten.bankmanagementsystem.model;

import java.util.Date;

public class Customer {
    private String customerId;
    private String name;
    private String phoneNumber;
    private String email;
    private String password;
    private Date createdAt;

    public Customer(String customerId, String name, String phoneNumber, String email, String password, Date createdAt){
        this.customerId = customerId;
        this.name=name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.createdAt = createdAt;
    }

}
