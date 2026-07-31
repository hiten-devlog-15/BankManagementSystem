package com.hiten.bankmanagementsystem.service;

import com.hiten.bankmanagementsystem.exception.CustomerNotFoundException;
import com.hiten.bankmanagementsystem.model.Customer;
import com.hiten.bankmanagementsystem.repository.CustomerRepository;
import com.hiten.bankmanagementsystem.util.IdGenerator;
import com.hiten.bankmanagementsystem.validator.Validator;

import java.time.LocalDate;
import java.util.List;

public class CustomerService {
    private final CustomerRepository customerRepository;
    private final IdGenerator idGenerator;
    private final Validator validator;

    public CustomerService(CustomerRepository customerRepository, IdGenerator idGenerator, Validator validator){
        this.customerRepository=customerRepository;
        this.idGenerator = idGenerator;
        this.validator=validator;
    }

    //Register Account
    public void registerCustomer(String name, String phoneNumber, String email, String password){ //For v1 --> boolean return
        LocalDate createdAt = LocalDate.now();
        validator.validateEmail(email);
        validator.validatePhoneNumber(phoneNumber);
        customerRepository.existsByEmail(email);
        customerRepository.existsByPhoneNumber(phoneNumber);
        Customer customer = new Customer(idGenerator.generateCustomerId(), name, phoneNumber, email, password, createdAt);
        customerRepository.saveCustomer(customer);
    }

    //Search Customer
    public Customer searchCustomer(int customerId){
        return customerRepository.findCustomerById(customerId);
    }

    //View All Customers(Admin)
    public List<Customer> viewAllCustomer(){
        return customerRepository.findAllCustomers();
    }
}