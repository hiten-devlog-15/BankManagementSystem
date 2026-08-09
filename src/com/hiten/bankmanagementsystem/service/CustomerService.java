package com.hiten.bankmanagementsystem.service;

import com.hiten.bankmanagementsystem.model.Customer;
import com.hiten.bankmanagementsystem.repository.CustomerRepository;
import com.hiten.bankmanagementsystem.validator.Validator;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class CustomerService {
    private final CustomerRepository customerRepository;
    private final Validator validator;

    public CustomerService(CustomerRepository customerRepository, Validator validator){
        this.customerRepository=customerRepository;
        this.validator=validator;
    }

    //Register Account
    public Customer registerCustomer(String name, String phoneNumber, String email, String password) throws SQLException { //For v1 --> boolean return
        LocalDate createdAt = LocalDate.now();
        validator.validateEmail(email);
        validator.validatePhoneNumber(phoneNumber);
        customerRepository.existsByEmail(email);
        customerRepository.existsByPhoneNumber(phoneNumber);
        Customer customer = new Customer(name, phoneNumber, email, password, createdAt);
        customerRepository.saveCustomer(customer);
        return customer;
    }

    //Search Customer
    public Customer searchCustomer(int customerId) throws SQLException {
        return customerRepository.findCustomerById(customerId);
    }

    //View All Customers(Admin)
    public List<Customer> viewAllCustomer() throws SQLException{
        return customerRepository.findAllCustomers();
    }
}