package com.hiten.bankmanagementsystem.service;

import com.hiten.bankmanagementsystem.model.Customer;
import com.hiten.bankmanagementsystem.repository.CustomerRepository;
import com.hiten.bankmanagementsystem.util.IdGenerator;
import com.hiten.bankmanagementsystem.validator.Validator;

import java.time.LocalDate;

public class CustomerService {

    private CustomerRepository customerRepository;
    private IdGenerator idGenerator;
    private Validator validator;

    public CustomerService(CustomerRepository customerRepository, IdGenerator idGenerator, Validator validator){
        this.customerRepository=customerRepository;
        this.idGenerator = idGenerator;
        this.validator=validator;
    }


    Customer customer;
    public boolean registerCustomer(String name, String phoneNumber, String email, String password){ //For v1 --> boolean return
        LocalDate createdAt = LocalDate.now();
        if(!validator.validateEmail(email) || !validator.validatePhoneNumber(phoneNumber) ||
                customerRepository.duplicateEmail(email) || customerRepository.duplicatePhoneNumber(phoneNumber)){
            return false;
        }
        customer = new Customer(idGenerator.generateCustomerId(), name, phoneNumber, email, password, createdAt);
        customerRepository.saveCustomer(customer);
        return true;
    }

}