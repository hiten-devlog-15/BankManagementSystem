package com.hiten.bankmanagementsystem.repository;
import java.net.ConnectException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.hiten.bankmanagementsystem.exception.CustomerNotFoundException;
import com.hiten.bankmanagementsystem.exception.DuplicateEmailException;
import com.hiten.bankmanagementsystem.exception.DuplicatePhoneNumberException;
import com.hiten.bankmanagementsystem.filepersistence.CustomerFilePersistence;
import com.hiten.bankmanagementsystem.model.Customer;
import com.hiten.bankmanagementsystem.util.DatabaseConnection;

public class CustomerRepository {

    private final CustomerFilePersistence customerFilePersistence;

    List<Customer> customerList;

    public CustomerRepository(CustomerFilePersistence customerFilePersistence) {
        this.customerFilePersistence = customerFilePersistence;
        customerList = customerFilePersistence.loadCustomers();
    }

    public void saveCustomer(Customer customer) throws SQLException {
        String query = "INSERT INTO customers(customer_name, phone_number, email, pass_word, created_at) VALUES(?, ?, ?, ?, ?)";
        try(Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
        ){
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getPhoneNumber());
            preparedStatement.setString(3, customer.getEmail());
            preparedStatement.setString(4, customer.getPassword());
            preparedStatement.setDate(5, Date.valueOf(customer.getCreatedAt()));
            preparedStatement.executeUpdate();

            try(ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if(resultSet.next()){
                    int generatedId = resultSet.getInt("customer_id");
                    customer.setCustomerId(generatedId);
                }
            }
        }
//        customerList.add(customer);
//        customerFilePersistence.saveCustomer(customer);
    }

    public Customer findCustomerByEmail(String email) throws SQLException{
        String query = "SELECT customer_id, customer_name, phone_number, email, pass_word, created_at FROM customers WHERE email = ?";
        try(Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)
        ){
            preparedStatement.setString(1, email);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                if(resultSet.next()){
                    return new Customer(resultSet.getInt("customer_id"),
                    resultSet.getString("customer_name"),
                    resultSet.getString("phone_number"),
                    resultSet.getString("email"),
                    resultSet.getString("pass_word"),
                    resultSet.getDate("created_at").toLocalDate());
                }
            }
        }

//        for(Customer customer : customerList){
//            if(customer.getEmail().equals(email)){ //Particular customer email equal to email im passing
//                return customer;
//            }
//        }
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

    public List<Customer> findAllCustomers(){
        return customerList;
    }


}
