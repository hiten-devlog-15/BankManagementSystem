package com.hiten.bankmanagementsystem;

import com.hiten.bankmanagementsystem.enums.AccountType;
import com.hiten.bankmanagementsystem.exception.BankException;
import com.hiten.bankmanagementsystem.filepersistence.AccountFilePersistence;
import com.hiten.bankmanagementsystem.filepersistence.CustomerFilePersistence;
import com.hiten.bankmanagementsystem.filepersistence.TransactionFilePersistence;
import com.hiten.bankmanagementsystem.model.Account;
import com.hiten.bankmanagementsystem.model.Customer;
import com.hiten.bankmanagementsystem.model.Transaction;
import com.hiten.bankmanagementsystem.repository.AccountRepository;
import com.hiten.bankmanagementsystem.repository.CustomerRepository;
import com.hiten.bankmanagementsystem.repository.TransactionRepository;
import com.hiten.bankmanagementsystem.service.AccountService;
import com.hiten.bankmanagementsystem.service.CustomerService;
import com.hiten.bankmanagementsystem.service.TransactionService;
import com.hiten.bankmanagementsystem.util.DatabaseConnection;
import com.hiten.bankmanagementsystem.util.IdGenerator;
import com.hiten.bankmanagementsystem.validator.Validator;


import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        CustomerRepository customerRepository = new CustomerRepository();
        AccountFilePersistence accountFilePersistence = new AccountFilePersistence(customerRepository);
        AccountRepository accountRepository = new AccountRepository(customerRepository);
        TransactionFilePersistence transactionFilePersistence = new TransactionFilePersistence(accountRepository);
        TransactionRepository transactionRepository = new TransactionRepository(transactionFilePersistence);
        IdGenerator idGenerator = new IdGenerator();
        Validator validator = new Validator();
        CustomerService customerService = new CustomerService(customerRepository, idGenerator, validator);
        AccountService accountService =new AccountService(customerRepository, accountRepository, transactionRepository,
                idGenerator, validator);
        TransactionService transactionService = new TransactionService(transactionRepository);

        Scanner scanner = new Scanner(System.in);

        boolean keepRunning = true;

        do{
            System.out.println("==========================\n" +
                    " BANK MANAGEMENT SYSTEM\n" +
                    "==========================\n" +
                    "1.  Register Customer\n" +
                    "2.  Create Account\n" +
                    "3.  Deposit\n" +
                    "4.  Withdraw\n" +
                    "5.  Transfer\n" +
                    "6.  View Passbook\n" +
                    "7.  View Account\n" +
                    "8.  Check Balance\n" +
                    "9.  Close Account\n" +
                    "10. Search Customer\n" +
                    "11. View All Customers\n" +
                    "12. View All Accounts\n" +
                    "13. View All Transactions\n" +
                    "14. Exit\n" +
                    "==========================\n" +
                    "Enter Choice:");

            int choice = scanner.nextInt();

            scanner.nextLine();

            switch (choice){
                case 1:// Create customer
                    System.out.println("Enter Name");
                    String name = scanner.nextLine();
                    System.out.println("Enter Phone Number");
                    String phoneNumber = scanner.next();
                    System.out.println("Enter Email");
                    String email = scanner.next();
                    System.out.println("Enter password");
                    String password = scanner.next();
                    try{
                        customerService.registerCustomer(name, phoneNumber, email, password);
                        System.out.println("Customer registered successfully. Your CustomerID is " +
                                idGenerator.getCustomerId());
                    } catch (BankException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 2://Create Account
                    System.out.println("Enter Customer ID");
                    int customerID = scanner.nextInt();
                    System.out.println("Enter Account Type: SAVINGS OR CURRENT");
                    AccountType accountType = AccountType.valueOf(scanner.next().toUpperCase());
                    System.out.println("Enter Initial Deposit");
                    int initialDeposit = scanner.nextInt();
                    try{
                        accountService.createAccount(customerID, accountType, initialDeposit);
                        System.out.println("Account created Successfully " + idGenerator.getAccountId());
                    }catch (BankException e){
                        System.out.println(e.getMessage());
                    }
                    break;

                case 3:
                    //Deposit code
                    System.out.println("Enter Account ID:");
                    int accountId = scanner.nextInt();
                    System.out.println("Enter Amount:");
                    int amount = scanner.nextInt();
                    System.out.println("Enter Password:");
                    password = scanner.next();
                    try{
                        accountService.deposit(accountId, amount, password);
                        System.out.println("Deposit Successful. Current Balance: " +
                                accountRepository.findAccountById(accountId).getCurrentBalance());
                    }catch (BankException e){
                        System.out.println(e.getMessage());
                    }
                    break;

                case 4:
                    //Withdraw code
                    System.out.println("Enter Account ID:");
                    accountId = scanner.nextInt();
                    System.out.println("Enter amount:");
                    amount = scanner.nextInt();
                    System.out.println("Enter Password:");
                    password = scanner.next();
                    try{
                        accountService.withdraw(accountId, amount, password);
                        System.out.println("Withdraw Successful. Current balance: " +
                                accountRepository.findAccountById(accountId).getCurrentBalance());
                    }catch (BankException e){
                        System.out.println(e.getMessage());
                    }
                    break;

                case 5:
                    //Transfer code
                    System.out.println("Enter your AccountID");
                    int senderAccountId = scanner.nextInt();
                    System.out.println("Enter Receiver's AccountID");
                    int receiverAccountId = scanner.nextInt();
                    System.out.println("Enter amount to be transferred");
                    int transferredAmount = scanner.nextInt();
                    System.out.println("Enter Password:");
                    password = scanner.next();
                    try{
                        accountService.transfer(senderAccountId, receiverAccountId, transferredAmount, password);
                        System.out.println("Transfer Successful");
                    }catch (BankException e){
                        System.out.println(e.getMessage());
                    }
                    break;

                case 6:
                    // View Transaction History or Passbook
                    System.out.println("Enter AccountID");
                    accountId = scanner.nextInt();
                    try{
                        List<Transaction> transactions = accountService.getTransactionHistory(accountId);
                        if(transactions != null){
                            for(Transaction transaction : transactions){
                                System.out.println(transaction);
                            }
                        }
                    }catch (BankException e){
                        System.out.println(e.getMessage());
                    }
                    break;

                case 7:// View Account Details
                    System.out.println("Enter AccountID");
                    accountId = scanner.nextInt();
                    try{
                        Account account = accountService.getAccountDetails(accountId);
                        System.out.println(account);

                    }catch (BankException e){
                        System.out.println(e.getMessage());
                    }

                    break;

                case 8://Check Balance
                    System.out.println("Enter AccountID");
                    accountId = scanner.nextInt();
                    try{
                        double balance = accountService.checkBalance(accountId);
                        System.out.println("Current Balance: " + balance);

                    }catch (BankException e){
                        System.out.println(e.getMessage());
                    }
                    break;

                case 9://Close Account
                    System.out.println("Enter AccountId");
                    accountId = scanner.nextInt();
                    System.out.println("Enter Password");
                    password = scanner.next();
                    try{
                        accountService.closeAccount(accountId, password);
                        System.out.println("Account Closed");
                    }catch (BankException e){
                        System.out.println(e.getMessage());
                    }
                    break;

                case 10: //Search Customer
                    System.out.println("Enter CustomerId");
                    customerID = scanner.nextInt();
                    try{
                        Customer customer = customerService.searchCustomer(customerID);
                        System.out.println("Customer found: " + customer);
                    }catch (BankException e){
                        System.out.println(e.getMessage());
                    }
                    break;

                case 11: //View All Customer
                    System.out.println("List of all customers");
                    List<Customer> customerList = customerService.viewAllCustomer();
                    if(customerList.isEmpty()){
                        System.out.println("No Customers available");
                    }
                    else{
                        for(Customer customerItem : customerList){
                            System.out.println(customerItem);
                        }
                    }
                    break;


                case 12: // View all Accounts
                    System.out.println("List of all Accounts");
                    List<Account> accountList = accountService.getAllAccounts();
                    if(accountList.isEmpty()){
                        System.out.println("No Accounts available");
                    }
                    else{
                        for(Account accountItem : accountList){
                            System.out.println(accountItem);
                        }
                    }
                    break;

                case 13: //View all Transactions
                    System.out.println("List of all Transactions");
                    List<Transaction> transactionList = transactionService.getAllTransactions();
                    if(transactionList.isEmpty()){
                        System.out.println("No Transactions available");
                    }
                    else {
                        for(Transaction transaction : transactionList){
                            System.out.println(transaction);
                        }
                    }
                    break;

                case 14: //Exit
                    System.out.println("Thank you for using Bank Management System.");
                    keepRunning = false;
                    break;

                default:
                    System.out.println("Invalid Choice");
            }

        }while (keepRunning);
    }
}