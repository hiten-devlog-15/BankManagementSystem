package com.hiten.bankmanagementsystem.repository;


import com.hiten.bankmanagementsystem.model.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerRepository {

    List<Customer> customerList = new ArrayList<>();
    public void saveCustomer(Customer customer){
        customerList.add(customer);
    }
}
