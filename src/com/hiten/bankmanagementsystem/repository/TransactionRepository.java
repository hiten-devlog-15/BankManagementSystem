package com.hiten.bankmanagementsystem.repository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.hiten.bankmanagementsystem.enums.TransactionType;
import com.hiten.bankmanagementsystem.filepersistence.TransactionFilePersistence;
import com.hiten.bankmanagementsystem.model.Account;
import com.hiten.bankmanagementsystem.model.Transaction;
import com.hiten.bankmanagementsystem.util.DatabaseConnection;

public class TransactionRepository {

    private final TransactionFilePersistence transactionFilePersistence;

    List<Transaction> transactionList;

    public TransactionRepository(TransactionFilePersistence transactionFilePersistence) {
        this.transactionFilePersistence = transactionFilePersistence;
        transactionList = transactionFilePersistence.loadTransactions();
    }

    public void saveTransaction(Transaction transaction) throws SQLException {
        String query = "INSERT INTO transactions(account_id, transaction_type, amount, date_of_transaction, balance_after_trasaction) VALUES (?, ?, ?, ?, ?)";
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

    public List<Transaction> findTransactionsByAccount(Account account){
        List<Transaction> transactionHistory = new ArrayList<>();
        for(Transaction transaction : transactionList){
            if(transaction.getAccount().equals(account)){
                transactionHistory.add(transaction);
            }
        }
        return transactionHistory;
    }

    public List<Transaction> findAllTransactions(){
        return transactionList;
    }

}
