package com.hiten.bankmanagementsystem.repository;

import com.hiten.bankmanagementsystem.exception.CustomerNotFoundException;
import com.hiten.bankmanagementsystem.exception.DuplicateEmailException;
import com.hiten.bankmanagementsystem.exception.DuplicatePhoneNumberException;
import com.hiten.bankmanagementsystem.model.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerRepository {

    List<Customer> customerList = new ArrayList<>();

    public void saveCustomer(Customer customer){
        customerList.add(customer);
    }

    public Customer findCustomerByEmail(String email){
        for(Customer customer : customerList){
            if(customer.getEmail().equals(email)){ //Particular customer email equal to email im passing
                return customer;
            }
        }
        return null;
    }

    public Customer findCustomerByPhoneNumber(String phoneNumber){
        for(Customer customer : customerList){
            if(customer.getPhoneNumber().equals(phoneNumber)){
                return customer;
            }
        }
        return null;
    }

    public Customer findCustomerById(int customerId){
        for(Customer customer : customerList){
            if(customer.getCustomerId() == customerId){
                return customer;
            }
        }
        throw new CustomerNotFoundException();
    }

    public void existsByEmail(String email){
        if(findCustomerByEmail(email) != null){
            throw new DuplicateEmailException();
        }
    }

    public void existsByPhoneNumber(String phoneNumber){
        if(findCustomerByPhoneNumber(phoneNumber) != null){
            throw new DuplicatePhoneNumberException();
        }
    }

    public boolean existsById(int customerId){
        return findCustomerById(customerId) != null;
    }


    public List<Customer> findAllCustomers(){
        return customerList;
    }


}
