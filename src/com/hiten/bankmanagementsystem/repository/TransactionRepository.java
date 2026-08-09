package com.hiten.bankmanagementsystem.repository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.hiten.bankmanagementsystem.enums.TransactionType;
import com.hiten.bankmanagementsystem.model.Account;
import com.hiten.bankmanagementsystem.model.Transaction;
import com.hiten.bankmanagementsystem.util.DatabaseConnection;

public class TransactionRepository {

//    private final TransactionFilePersistence transactionFilePersistence;
    private final AccountRepository accountRepository;

    public TransactionRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
//        transactionList = transactionFilePersistence.loadTransactions();
    }

    public void saveTransaction(Transaction transaction) throws SQLException {
        String query = "INSERT INTO transactions(account_id, transaction_type, amount, date_of_transaction, balance_after_transaction) VALUES (?, ?, ?, ?, ?)";
        try(Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
        ){
            preparedStatement.setInt(1, transaction.getAccount().getAccountId());
            preparedStatement.setString(2, transaction.getTransactionType().name());
            preparedStatement.setDouble(3, transaction.getAmount());
            preparedStatement.setDate(4, Date.valueOf(transaction.getDate()));
            preparedStatement.setDouble(5, transaction.getBalanceAfterTransaction());
            preparedStatement.executeUpdate();
            try(ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    int generatedId = resultSet.getInt("transaction_id");
                    transaction.setTransactionId(generatedId);
                }
            }
        }
//        transactionList.add(transaction);
//        transactionFilePersistence.saveTransaction(transaction);
    }

    public List<Transaction> findTransactionsByAccount(Account account) throws SQLException{
        List<Transaction> transactionHistory = new ArrayList<>();
        String query = "SELECT transaction_id, account_id, transaction_type, amount, date_of_transaction, balance_after_transaction FROM transactions WHERE account_id = ?";
        try(Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)
        ){
            preparedStatement.setInt(1, account.getAccountId());
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while(resultSet.next()){
                    int accountId = resultSet.getInt("account_id");
                    Account account1 = accountRepository.findAccountById(accountId);
                    TransactionType transactionType = TransactionType.valueOf(resultSet.getString("transaction_type"));
                    transactionHistory.add(new Transaction(resultSet.getInt("transaction_id"),
                            account1,
                            transactionType,
                            resultSet.getInt("amount"),
                            resultSet.getDate("date_of_transaction").toLocalDate(),
                            resultSet.getDouble("balance_after_transaction"))
                    );
                }
            }
        }
//        for(Transaction transaction : transactionList){
//            if(transaction.getAccount().equals(account)){
//                transactionHistory.add(transaction);
//            }
//        }
        return transactionHistory;
    }

    public List<Transaction> findAllTransactions() throws SQLException{
        List<Transaction> transactionHistory = new ArrayList<>();
        String query = "SELECT transaction_id, account_id, transaction_type, amount, date_of_transaction, balance_after_transaction FROM transactions";
        try(Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery()
        ){
            while(resultSet.next()){
                int accountId = resultSet.getInt("account_id");
                Account account1 = accountRepository.findAccountById(accountId);
                TransactionType transactionType = TransactionType.valueOf(resultSet.getString("transaction_type"));
                transactionHistory.add(new Transaction(resultSet.getInt("transaction_id"),
                        account1,
                        transactionType,
                        resultSet.getDouble("amount"),
                        resultSet.getDate("date_of_transaction").toLocalDate(),
                        resultSet.getDouble("balance_after_transaction"))
                );
            }
        }
        return transactionHistory;
    }

}
