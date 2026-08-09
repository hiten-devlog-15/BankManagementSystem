package com.hiten.bankmanagementsystem.service;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import com.hiten.bankmanagementsystem.enums.AccountStatus;
import com.hiten.bankmanagementsystem.enums.AccountType;
import com.hiten.bankmanagementsystem.enums.TransactionType;
import com.hiten.bankmanagementsystem.exception.AccountAlreadyExistsException;
import com.hiten.bankmanagementsystem.exception.InsufficientBalanceException;
import com.hiten.bankmanagementsystem.exception.InvalidPasswordException;
import com.hiten.bankmanagementsystem.model.Account;
import com.hiten.bankmanagementsystem.model.Customer;
import com.hiten.bankmanagementsystem.model.Transaction;
import com.hiten.bankmanagementsystem.repository.AccountRepository;
import com.hiten.bankmanagementsystem.repository.CustomerRepository;
import com.hiten.bankmanagementsystem.repository.TransactionRepository;
import com.hiten.bankmanagementsystem.validator.Validator;

public class AccountService {
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final Validator validator;
    private final TransactionRepository transactionRepository;

    public AccountService(CustomerRepository customerRepository, AccountRepository accountRepository,  TransactionRepository transactionRepository,
                          Validator validator){
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.validator = validator;
    }

    // Create Account
    public Account createAccount(int customerId, AccountType accountType, int initialDeposit) throws SQLException {
        Customer customer = customerRepository.findCustomerById(customerId);
        if (accountRepository.existsByCustomer(customer)) {
            throw new AccountAlreadyExistsException();
        }
        validator.validateInitialDeposit(accountType, initialDeposit);
        Account account = new Account(customer, accountType, initialDeposit, AccountStatus.ACTIVE, LocalDate.now());
        accountRepository.saveAccount(account);
        return account;
    }

    //Verify Password
    private void verifyPassword(Account account, String password) {
        if(!account.getCustomer().getPassword().equals(password)){
            throw new InvalidPasswordException();
        }
    }

    //Deposit Operation
    public void deposit(int accountId, int amount, String password) throws SQLException {
        Account account = accountRepository.findAccountById(accountId);
        validator.isAccountActive(account);
        validator.validateAmount(amount);
        verifyPassword(account, password);
        account.deposit(amount);
        accountRepository.updateBalance(account.getAccountId(), account.getCurrentBalance());
        createTransaction(account, TransactionType.DEPOSIT, amount);
    }

    //Withdraw Operation
    public void withdraw(int accountId, int amount, String password) throws SQLException {
        Account account = accountRepository.findAccountById(accountId);
        validator.isAccountActive(account);
        validator.validateAmount(amount);
        verifyPassword(account, password);
        if(account.getCurrentBalance()<amount){
            throw new InsufficientBalanceException();
        }
        account.withdraw(amount);
        accountRepository.updateBalance(account.getAccountId(), account.getCurrentBalance());
        createTransaction(account, TransactionType.WITHDRAW, amount);
    }

    //Create Transaction
    private void createTransaction(Account account, TransactionType type, int amount) throws SQLException{
        LocalDate date = LocalDate.now();
        Transaction transaction = new Transaction(account, type, amount, date, account.getCurrentBalance());
        transactionRepository.saveTransaction(transaction);
    }

    //Transfer Money Operation
    public void transfer(int senderAccountId, int receiverAccountId, int amount, String password) throws SQLException {
        Account senderAccount = accountRepository.findAccountById(senderAccountId);
        Account receiverAccount = accountRepository.findAccountById(receiverAccountId);
        validator.isAccountActive(senderAccount);
        validator.isAccountActive(receiverAccount);
        validator.checkSameAccount(senderAccount, receiverAccount);
        validator.validateAmount(amount);
        verifyPassword(senderAccount, password);
        if(senderAccount.getCurrentBalance() < amount){
            throw new InsufficientBalanceException();
        }
        senderAccount.withdraw(amount);
        accountRepository.updateBalance(senderAccount.getAccountId(), senderAccount.getCurrentBalance());
        receiverAccount.deposit(amount);
        accountRepository.updateBalance(receiverAccount.getAccountId(), receiverAccount.getCurrentBalance());
        createTransaction(senderAccount, TransactionType.TRANSFER_OUT, amount);
        createTransaction(receiverAccount, TransactionType.TRANSFER_IN, amount);
    }

    //View Transaction History
    public List<Transaction> getTransactionHistory(int accountId) throws SQLException {
        Account account = accountRepository.findAccountById(accountId);
        return transactionRepository.findTransactionsByAccount(account);
    }

    //View Account
    public Account getAccountDetails(int accountId) throws SQLException {
        return accountRepository.findAccountById(accountId);
    }

    //Check Balance
    public double checkBalance(int accountId) throws SQLException {
        Account account = accountRepository.findAccountById(accountId);
        return account.getCurrentBalance();
    }

    //Close Account
    public void closeAccount(int accountId, String password) throws SQLException {
        Account account = accountRepository.findAccountById(accountId);
        validator.isAccountActive(account);
        verifyPassword(account, password);
        validator.checkBalanceIsZero(account);
        account.closeAccount();
        accountRepository.updateStatus(account.getAccountId(), account.getAccountStatus());
    }

    //View All Accounts(Admin)
    public List<Account> getAllAccounts() throws SQLException {
        return accountRepository.findAllAccounts();
    }
}
