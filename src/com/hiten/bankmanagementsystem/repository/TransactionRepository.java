package com.hiten.bankmanagementsystem.repository;

import com.hiten.bankmanagementsystem.model.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionRepository {
    List<Transaction> transactionList = new ArrayList<>();

    public void saveTransaction(Transaction transaction){
        transactionList.add(transaction);
    }
}
