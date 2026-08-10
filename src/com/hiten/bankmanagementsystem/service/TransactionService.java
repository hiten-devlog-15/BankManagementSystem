package com.hiten.bankmanagementsystem.service;
import java.sql.SQLException;
import java.util.List;

import com.hiten.bankmanagementsystem.model.Transaction;
import com.hiten.bankmanagementsystem.repository.TransactionRepository;

public class TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository){
        this.transactionRepository = transactionRepository;
    }

    // View All Transactions
    public List<Transaction> getAllTransactions() throws SQLException {
        return transactionRepository.findAllTransactions();
    }
}
