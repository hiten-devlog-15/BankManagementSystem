package com.hiten.bankmanagementsystem.repository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.hiten.bankmanagementsystem.exception.CustomerNotFoundException;
import com.hiten.bankmanagementsystem.exception.DuplicateEmailException;
import com.hiten.bankmanagementsystem.exception.DuplicatePhoneNumberException;
import com.hiten.bankmanagementsystem.model.Customer;
import com.hiten.bankmanagementsystem.util.DatabaseConnection;

public class CustomerRepository {

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

    public Customer findCustomerByPhoneNumber(String phoneNumber) throws SQLException{
        String query = "SELECT customer_id, customer_name, phone_number, email, pass_word, created_at FROM customers WHERE phone_number = ?";
        try(Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)
        ){
            preparedStatement.setString(1, phoneNumber);
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
//            if(customer.getPhoneNumber().equals(phoneNumber)){
//                return customer;
//            }
//        }
        return null;
    }

    public Customer findCustomerById(int customerId) throws SQLException{
        String query = "SELECT customer_id, customer_name, phone_number, email, pass_word, created_at FROM customers WHERE customer_id = ?";
        try(Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)
        ){
            preparedStatement.setInt(1, customerId);
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
//            if(customer.getCustomerId() == customerId){
//                return customer;
//            }
//        }
        throw new CustomerNotFoundException();
    }

    public void existsByEmail(String email) throws SQLException{
        if(findCustomerByEmail(email) != null){
            throw new DuplicateEmailException();
        }
    }

    public void existsByPhoneNumber(String phoneNumber) throws SQLException {
        if(findCustomerByPhoneNumber(phoneNumber) != null){
            throw new DuplicatePhoneNumberException();
        }
    }

    public List<Customer> findAllCustomers() throws SQLException{
        List<Customer> customerList = new ArrayList<>();
        String query = "SELECT customer_id, customer_name, phone_number, email, pass_word, created_at FROM customers";
        try(Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery()){

            while(resultSet.next()){
                customerList.add(new Customer(resultSet.getInt("customer_id"),
                        resultSet.getString("customer_name"),
                        resultSet.getString("phone_number"),
                        resultSet.getString("email"),
                        resultSet.getString("pass_word"),
                        resultSet.getDate("created_at").toLocalDate()));
            }

        }
        return customerList;
    }
}