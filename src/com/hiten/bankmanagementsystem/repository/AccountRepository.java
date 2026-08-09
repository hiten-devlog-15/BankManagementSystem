package com.hiten.bankmanagementsystem.repository;

import com.hiten.bankmanagementsystem.enums.AccountStatus;
import com.hiten.bankmanagementsystem.enums.AccountType;
import com.hiten.bankmanagementsystem.exception.AccountNotFoundException;
import com.hiten.bankmanagementsystem.filepersistence.AccountFilePersistence;
import com.hiten.bankmanagementsystem.model.Account;
import com.hiten.bankmanagementsystem.model.Customer;
import com.hiten.bankmanagementsystem.util.DatabaseConnection;


import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class AccountRepository {

    private final CustomerRepository customerRepository;

    public AccountRepository(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
//        accountList = accountFilePersistence.loadAccounts();
    }

    public void saveAccount(Account account) throws SQLException {
        String query = "INSERT INTO accounts(customer_id, account_type, current_balance, account_status, created_at) VALUES(?, ?, ?, ?, ?) ";
        try(Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
        ){
            preparedStatement.setInt(1, account.getCustomer().getCustomerId());
            preparedStatement.setString(2, account.getAccountType().name());
            preparedStatement.setDouble(3, account.getCurrentBalance());
            preparedStatement.setString(4, account.getAccountStatus().name());
            preparedStatement.setDate(5, Date.valueOf(account.getCreatedAt()));
            preparedStatement.executeUpdate();
            try(ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if(resultSet.next()){
                    int generatedId = resultSet.getInt("account_id");
                    account.setAccountId(generatedId);
                }
            }
        }
//        accountList.add(account);
//        accountFilePersistence.saveAccount(account);
    }

    public boolean existsByCustomer(Customer customer) throws SQLException{
        String query = "SELECT account_id FROM accounts WHERE customer_id = ?";
        try(Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)
            ){
            preparedStatement.setInt(1, customer.getCustomerId());
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){
                    return true;
                }
            }
        }

//        for(Account account : accountList){
//            if(account.getCustomer().equals(customer)){
//                return true;
//            }
//        }
        return false;
    }

    public Account findAccountById(int accountId) throws SQLException{
        String query = "SELECT account_id, customer_id, account_type, current_balance, account_status, created_at FROM accounts WHERE account_id = ? ";
        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)
                ){
            preparedStatement.setInt(1, accountId);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){
                    int customerId = resultSet.getInt("customer_id");
                    Customer customer = customerRepository.findCustomerById(customerId);
                    AccountType accountType = AccountType.valueOf(resultSet.getString("account_type"));
                    AccountStatus accountStatus = AccountStatus.valueOf(resultSet.getString("account_status"));

                    return new Account(resultSet.getInt("account_id"),
                            customer,
                            accountType,
                            resultSet.getDouble("current_balance"),
                            accountStatus,
                            resultSet.getDate("created_at").toLocalDate());
                }
            }
        }
//        for(Account account : accountList){
//            if(account.getAccountId() == accountId){
//                return account;
//            }
//        }
        throw new AccountNotFoundException();
    }

    public List<Account> findAllAccounts() throws SQLException{
        List<Account> accountList = new ArrayList<>();
        String query = "SELECT account_id, customer_id, account_type, current_balance, account_status, created_at FROM accounts";
        try(Connection connection = DatabaseConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery()
        ){
            while(resultSet.next()){
                int customerId = resultSet.getInt("customer_id");
                Customer customer = customerRepository.findCustomerById(customerId);
                AccountType accountType = AccountType.valueOf(resultSet.getString("account_type"));
                AccountStatus accountStatus = AccountStatus.valueOf(resultSet.getString("account_status"));
                accountList.add(new Account(resultSet.getInt("account_id"),
                            customer,
                            accountType,
                            resultSet.getDouble("current_balance"),
                            accountStatus,
                            resultSet.getDate("created_at").toLocalDate())
                            );
                }

        }
        return accountList;
    }

}
