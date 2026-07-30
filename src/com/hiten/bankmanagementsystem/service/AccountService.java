package com.hiten.bankmanagementsystem.service;
import java.time.LocalDate;
import java.util.List;

import com.hiten.bankmanagementsystem.enums.AccountStatus;
import com.hiten.bankmanagementsystem.enums.AccountType;
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
    public boolean createAccount(int customerId, AccountType accountType, int initialDeposit){
        LocalDate createdAt = LocalDate.now();
        int currentBalance = initialDeposit; //Initially when the acc is created, later it can increase and decrease depending on operation
        AccountStatus accountStatus = AccountStatus.ACTIVE;
        Customer customer = customerRepository.findCustomerById(customerId);
        if(!customerRepository.existsById(customerId) ||
                accountRepository.existsByCustomer(customer)){

            return false;
        }

        else{
            if(accountType == AccountType.SAVINGS && initialDeposit >= 2000){
                Account account = new Account(customer, idGenerator.generateAccountId(),
                        accountType, currentBalance, accountStatus, createdAt); //Bcoz initialDeposit itself is current Balance
                // when acc is just created
                accountRepository.saveAccount(account);
                return true;
            } else if (accountType == AccountType.CURRENT && initialDeposit >= 5000) {
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
        if (account == null || !validator.isAccountActive(account) || !validator.validateAmount(amount)) {
            return false;
        }
        account.deposit(amount);
        createTransaction(account, TransactionType.DEPOSIT, amount);
        return true;
    }

    // Withdraw Operation
    public boolean withdraw(int accountId, int amount){
        Account account = accountRepository.findAccountById(accountId);
        if(account == null || !validator.isAccountActive(account) || !validator.validateAmount(amount) || account.getCurrentBalance()<amount){
            return false;
        }
        account.withdraw(amount);
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

        if(senderAccount == null || !validator.isAccountActive(senderAccount) || receiverAccount == null || !validator.isAccountActive(receiverAccount) || !validator.validateAmount(amount) ||
                senderAccount.getCurrentBalance() < amount || senderAccountId == receiverAccountId){
            return false;
        }
        senderAccount.withdraw(amount);
        receiverAccount.deposit(amount);
        createTransaction(senderAccount, TransactionType.TRANSFER_OUT, amount);
        createTransaction(receiverAccount, TransactionType.TRANSFER_IN, amount);
        return true;
    }


    public List<Transaction> getTransactionHistory(int accountId){
        Account account = accountRepository.findAccountById(accountId);

        if(account == null){
            return null;
        }
        return transactionRepository.findTransactionsByAccount(account);
    }

    // View Account
    public Account getAccountDetails(int accountId){
        Account account = accountRepository.findAccountById(accountId);
        if(account == null){
            return null;
        }
        return account;
    }

    public int checkBalance(int accountId){
        Account account = accountRepository.findAccountById(accountId);
        if(account != null){
            return account.getCurrentBalance();
        }
        return -1;
    }

    public boolean closeAccount(int accountId){
        Account account = accountRepository.findAccountById(accountId);
        if(account == null || !validator.isAccountActive(account) || account.getCurrentBalance() > 0){
           return false;
        }
        account.closeAccount();
        return true;
    }


    public List<Account> getAllAccounts(){
        return accountRepository.findAllAccounts();
    }
}
