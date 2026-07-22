package com.hiten.bankmanagementsystem.repository;

import com.hiten.bankmanagementsystem.model.Account;
import com.hiten.bankmanagementsystem.model.Customer;

import java.util.ArrayList;
import java.util.List;


public class AccountRepository {

    private Customer customer;
    List<Account> accountList = new ArrayList<>();

    public void saveAccount(Account account){
        accountList.add(account);
    }

    public boolean findAccountByCustomer(Customer customer){
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
        return null;
    }

    public boolean accountExist(int accountId){
        return findAccountById(accountId) != null;
    }



}
