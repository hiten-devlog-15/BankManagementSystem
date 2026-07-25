package com.hiten.bankmanagementsystem.service;
import java.time.LocalDate;
import java.util.List;

import com.hiten.bankmanagementsystem.enums.TransactionType;
import com.hiten.bankmanagementsystem.model.Account;
import com.hiten.bankmanagementsystem.model.Customer;
import com.hiten.bankmanagementsystem.model.Transaction;
import com.hiten.bankmanagementsystem.repository.AccountRepository;
import com.hiten.bankmanagementsystem.repository.CustomerRepository;
import com.hiten.bankmanagementsystem.repository.TransactionRepository;
import com.hiten.bankmanagementsystem.util.IdGenerator;
import com.hiten.bankmanagementsystem.validator.Validator;



public class
AccountService {

    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;
    private IdGenerator idGenerator;
    private Validator validator;
    private TransactionRepository transactionRepository;

    public AccountService(CustomerRepository customerRepository, AccountRepository accountRepository,  TransactionRepository transactionRepository, IdGenerator idGenerator,
                          Validator validator){
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.idGenerator = idGenerator;
        this.validator = validator;
    }

    // Register Account
    public boolean registerAccount(int customerId, String accountType, int initialDeposit){
        LocalDate createdAt = LocalDate.now();
        int currentBalance = initialDeposit; //Initially when the acc is created, later it can increase and decrease depending on operation
        String accountStatus = "Active";
        Customer customer = customerRepository.findCustomerById(customerId);
        if(!customerRepository.existsId(customerId) ||
                accountRepository.findAccountByCustomer(customer)){

            return false;
        }

        else{
            if(accountType.equals("SAVINGS") && initialDeposit >= 2000){
                Account account = new Account(customer, idGenerator.generateAccountId(),
                        accountType, currentBalance, accountStatus, createdAt); //Bcoz initialDeposit itself is current Balance
                // when acc is just created
                accountRepository.saveAccount(account);
                return true;
            } else if (accountType.equals("CURRENT") && initialDeposit >= 5000) {
                Account account = new Account(customer, idGenerator.generateAccountId(),
                        accountType, currentBalance, accountStatus, createdAt); //Bcoz initialDeposit itself is current Balance
                // when acc is just created
                accountRepository.saveAccount(account);
                return true;
            }
            return false;
        }
    }


    // Deposit Operation
    public boolean deposit(int accountId, int amount) {
        Account account = accountRepository.findAccountById(accountId);
        if (account == null || !validator.validateAmount(amount)) {
            return false;
        }
        account.depositAmount(amount);
        createTransaction(account, TransactionType.DEPOSIT, amount);
        return true;
    }

    // Withdraw Operation
    public boolean withdraw(int accountId, int amount){
        Account account = accountRepository.findAccountById(accountId);
        if(account == null || !validator.validateAmount(amount) || account.getCurrentBalance()<amount){
            return false;
        }
        account.withdrawAmount(amount);
        createTransaction(account, TransactionType.WITHDRAW, amount);
        return true;
    }

    // Create Transaction
    private void createTransaction(Account account, TransactionType type, int amount){
        int transactionId = idGenerator.generateTransactionId();
        LocalDate date = LocalDate.now();
        Transaction transaction = new Transaction(transactionId, account, type, amount, date, account.getCurrentBalance());
        transactionRepository.saveTransaction(transaction);
    }

    // Transfer Money Operation
    public boolean transfer(int senderAccountId, int receiverAccountId, int amount){
        Account senderAccount = accountRepository.findAccountById(senderAccountId);
        Account receiverAccount = accountRepository.findAccountById(receiverAccountId);

        if(senderAccount == null || receiverAccount == null || !validator.validateAmount(amount) ||
                senderAccount.getCurrentBalance() < amount || senderAccountId == receiverAccountId){
            return false;
        }
        senderAccount.withdrawAmount(amount);
        receiverAccount.depositAmount(amount);
        createTransaction(senderAccount, TransactionType.TRANSFER_OUT, amount);
        createTransaction(receiverAccount, TransactionType.TRANSFER_IN, amount);
        return true;
    }


    public List<Transaction> viewTransactionHistory(int accountId){
        Account account = accountRepository.findAccountById(accountId);

        if(account == null){
            return null;
        }
        return transactionRepository.findTransactionsByAccount(account);
    }

    public Account viewAccountDetails(int accountId){
        Account account = accountRepository.findAccountById(accountId);
        if(account == null){
            return null;
        }
        return account;
    }

}
