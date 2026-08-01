package com.hiten.bankmanagementsystem.filepersistence;

import com.hiten.bankmanagementsystem.enums.AccountStatus;
import com.hiten.bankmanagementsystem.enums.AccountType;
import com.hiten.bankmanagementsystem.model.Account;
import com.hiten.bankmanagementsystem.model.Customer;
import com.hiten.bankmanagementsystem.repository.CustomerRepository;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AccountFilePersistence {

    private final CustomerRepository customerRepository;
    public AccountFilePersistence(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    private static final String FILE_PATH = "accounts.txt";

    public void saveAccount(Account account){
        String line = account.getAccountId() + "," +
                        account.getCustomer().getCustomerId() + "," +
                        account.getAccountType() + "," +
                        account.getCurrentBalance() + "," +
                        account.getAccountStatus() + "," +
                        account.getCreatedAt();
        try(FileWriter fileWriter = new FileWriter(FILE_PATH, true)){
            fileWriter.write(line);
            fileWriter.write(System.lineSeparator());
        }catch (IOException e){
            throw new RuntimeException("Unable to save account.", e);
        }
    }

    public List<Account> loadAccounts(){
        List<Account> accountList = new ArrayList<>();
        File file = new File(FILE_PATH);
        if(!file.exists()){
            return accountList;
        }
        try(
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                ) {
            String line;
            while((line = bufferedReader.readLine()) != null){
                String[] accountData = line.split(",");

                int accountId = Integer.parseInt(accountData[0]);
                int customerId = Integer.parseInt(accountData[1]);
                Customer customer = customerRepository.findCustomerById(customerId);
                AccountType accountType = AccountType.valueOf(accountData[2]);
                int currentBalance = Integer.parseInt(accountData[3]);
                AccountStatus accountStatus = AccountStatus.valueOf(accountData[4]);
                LocalDate createdAt = LocalDate.parse(accountData[5]);
                Account account =  new Account(customer, accountId, accountType, currentBalance, accountStatus, createdAt);
                accountList.add(account);
            }
        }catch (IOException e){
            throw new RuntimeException("Unable to load accounts.", e);
        }
        return accountList;
    }
}
