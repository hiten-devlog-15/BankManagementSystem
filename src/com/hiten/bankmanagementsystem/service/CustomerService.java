package com.hiten.bankmanagementsystem.service;

import com.hiten.bankmanagementsystem.model.Customer;
import com.hiten.bankmanagementsystem.repository.CustomerRepository;
import com.hiten.bankmanagementsystem.util.IdGenerator;
import com.hiten.bankmanagementsystem.validator.Validator;

import java.time.LocalDate;
import java.util.List;

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
                customerRepository.existsByEmail(email) || customerRepository.existsByPhoneNumber(phoneNumber)){
            return false;
        }
        customer = new Customer(idGenerator.generateCustomerId(), name, phoneNumber, email, password, createdAt);
        customerRepository.saveCustomer(customer);
        return true;
    }

    //Search Customer
    public Customer searchCustomer(int customerId){
        customer = customerRepository.findCustomerById(customerId);
        if(customer != null){
            return customer;
        }
        return null;
    }

    public List<Customer> viewAllCustomer(){
        return customerRepository.findAllCustomers();
    }


}