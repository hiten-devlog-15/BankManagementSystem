package com.hiten.bankmanagementsystem.filepersistence;

import com.hiten.bankmanagementsystem.enums.TransactionType;
import com.hiten.bankmanagementsystem.model.Account;
import com.hiten.bankmanagementsystem.model.Transaction;
import com.hiten.bankmanagementsystem.repository.AccountRepository;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransactionFilePersistence {

    private final AccountRepository accountRepository;
    public TransactionFilePersistence(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    private static final String FILE_PATH = "transactions.txt";

    public void saveTransaction(Transaction transaction){
        String line = transaction.getTransactionId() + "," +
                transaction.getAccount().getAccountId() + "," +
                transaction.getTransactionType() + "," +
                transaction.getAmount() + "," +
                transaction.getDate() + "," +
                transaction.getBalanceAfterTransaction();
        try(FileWriter fileWriter = new FileWriter(FILE_PATH, true)){
            fileWriter.write(line);
            fileWriter.write(System.lineSeparator());
        } catch (IOException e) {
            throw new RuntimeException("Unable to save transaction", e);
        }
    }

    public List<Transaction> loadTransactions(){
        List<Transaction> transactionList = new ArrayList<>();

        File file = new File(FILE_PATH);
        if(!file.exists()){
            return transactionList;
        }
        try(
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                ) {
            String line;
            while((line = bufferedReader.readLine()) != null){
                String[] transactionData = line.split(",");
                int transactionId = Integer.parseInt(transactionData[0]);
                int accountId = Integer.parseInt(transactionData[1]);
                Account account = accountRepository.findAccountById(accountId);
                TransactionType transactionType = TransactionType.valueOf(transactionData[2]);
                int amount = Integer.parseInt(transactionData[3]);
                LocalDate date = LocalDate.parse(transactionData[4]);
                int balanceAfterTransaction = Integer.parseInt(transactionData[5]);
                Transaction transaction = new Transaction(transactionId, account, transactionType, amount, date,
                        balanceAfterTransaction);
                transactionList.add(transaction);
            }
        } catch (IOException e) {
            throw new RuntimeException("Unable to load transactions", e);
        }
        return transactionList;
    }
}
