package com.hiten.bankmanagementsystem.repository;

import com.hiten.bankmanagementsystem.exception.AccountNotFoundException;
import com.hiten.bankmanagementsystem.model.Account;
import com.hiten.bankmanagementsystem.model.Customer;

import java.util.ArrayList;
import java.util.List;


public class AccountRepository {

    List<Account> accountList = new ArrayList<>();

    public void saveAccount(Account account){
        accountList.add(account);
    }

    public boolean existsByCustomer(Customer customer){
        for(Account account : accountList){
            if(account.getCustomer().equals(customer)){
                return true;
            }
        }
        return false;
    }

    public Account findAccountById(int accountId){
        for(Account account : accountList){
            if(account.getAccountId() == accountId){
                return account;
            }
        }
        throw new AccountNotFoundException();
    }

    public List<Account> findAllAccounts(){
        return accountList;
    }

}
