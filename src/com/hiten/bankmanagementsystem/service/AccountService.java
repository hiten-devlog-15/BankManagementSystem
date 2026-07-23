package com.hiten.bankmanagementsystem.service;

import com.hiten.bankmanagementsystem.model.Account;
import com.hiten.bankmanagementsystem.model.Customer;
import com.hiten.bankmanagementsystem.repository.AccountRepository;
import com.hiten.bankmanagementsystem.repository.CustomerRepository;
import com.hiten.bankmanagementsystem.util.IdGenerator;
import com.hiten.bankmanagementsystem.validator.Validator;

import java.time.LocalDate;

public class
AccountService {

    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;
    private IdGenerator idGenerator;
    private Validator validator;

    public AccountService(CustomerRepository customerRepository, AccountRepository accountRepository,  IdGenerator idGenerator, Validator validator){
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.idGenerator = idGenerator;
        this.validator = validator;
    }

    public boolean createAccount(int customerId, String accountType, int initialDeposit){
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


    public boolean deposit(int accountId, int amount) {
        Account account = accountRepository.findAccountById(accountId);
        if (account == null || !validator.validateAmount(amount)) {
            return false;
        }
        account.depositAmount(amount);
        return true;
    }

    public boolean withdraw(int accountId, int amount){
        Account account = accountRepository.findAccountById(accountId);
        if(account == null || !validator.validateAmount(amount) || account.getCurrentBalance()>=amount){
            return false;
        }
        account.withdrawAmount(amount);
        return true;
    }

}
