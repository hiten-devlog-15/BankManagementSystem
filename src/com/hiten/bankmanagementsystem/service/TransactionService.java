package com.hiten.bankmanagementsystem.service;

import com.hiten.bankmanagementsystem.model.Transaction;
import com.hiten.bankmanagementsystem.repository.TransactionRepository;

import java.util.List;

public class TransactionService {

    private TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository){
        this.transactionRepository = transactionRepository;
    }



    public List<Transaction> viewTransactions(){
        return transactionRepository.viewTransactionList();
    }

}
