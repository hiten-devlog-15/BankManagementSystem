package com.hiten.bankmanagementsystem.repository;
import java.util.ArrayList;
import java.util.List;

import com.hiten.bankmanagementsystem.filepersistence.TransactionFilePersistence;
import com.hiten.bankmanagementsystem.model.Account;
import com.hiten.bankmanagementsystem.model.Transaction;

public class TransactionRepository {

    private final TransactionFilePersistence transactionFilePersistence;

    List<Transaction> transactionList;

    public TransactionRepository(TransactionFilePersistence transactionFilePersistence) {
        this.transactionFilePersistence = transactionFilePersistence;
        transactionList = transactionFilePersistence.loadTransactions();
    }

    public void saveTransaction(Transaction transaction){
        transactionList.add(transaction);
        transactionFilePersistence.saveTransaction(transaction);
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
