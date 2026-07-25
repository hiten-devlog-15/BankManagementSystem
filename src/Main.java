import com.hiten.bankmanagementsystem.model.Account;
import com.hiten.bankmanagementsystem.model.Transaction;
import com.hiten.bankmanagementsystem.repository.AccountRepository;
import com.hiten.bankmanagementsystem.repository.CustomerRepository;
import com.hiten.bankmanagementsystem.repository.TransactionRepository;
import com.hiten.bankmanagementsystem.service.AccountService;
import com.hiten.bankmanagementsystem.service.CustomerService;
import com.hiten.bankmanagementsystem.util.IdGenerator;
import com.hiten.bankmanagementsystem.validator.Validator;


import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        CustomerRepository customerRepository = new CustomerRepository();
        AccountRepository accountRepository = new AccountRepository();
        TransactionRepository transactionRepository = new TransactionRepository();
        IdGenerator idGenerator = new IdGenerator();
        Validator validator = new Validator();
        CustomerService customerService = new CustomerService(customerRepository, idGenerator, validator);
        AccountService accountService =new AccountService(customerRepository, accountRepository, transactionRepository,
                idGenerator, validator);

        Scanner scanner = new Scanner(System.in);

        boolean keepRunning = true;

        do{
            System.out.println("==========================\n" +
                    " BANK MANAGEMENT SYSTEM\n" +
                    "==========================\n" +
                    "1. Register Customer\n" +
                    "2. Create Account\n" +
                    "3. Deposit\n" +
                    "4. Withdraw\n" +
                    "5. Transfer\n" +
                    "6. View Account\n" +
                    "7. View Passbook\n" +
                    "8. Exit\n" +
                    "==========================\n" +
                    "Enter Choice:");

            int choice = scanner.nextInt();

            scanner.nextLine();

            switch (choice){
                case 1:
                    System.out.println("Enter Name");
                    String name = scanner.nextLine();

                    System.out.println("Enter Phone Number");
                    String phoneNumber = scanner.next();

                    System.out.println("Enter Email");
                    String email = scanner.next();

                    System.out.println("Enter password");
                    String password = scanner.next();

                    boolean registered = customerService.registerCustomer(name, phoneNumber, email, password);

                    if (registered) {
                        System.out.println("Customer registered successfully. Your CustomerID is " + idGenerator.getCustomerId());
                    } else {
                        System.out.println("Invalid email.");
                    }
                    break;

                case 2:
                    System.out.println("Enter Customer ID");
                    int customerID = scanner.nextInt();

                    System.out.println("Enter Account Type: SAVINGS OR CURRENT");
                    String accountType = scanner.next();

                    System.out.println("Enter Initial Deposit");
                    int initialDeposit = scanner.nextInt();

                    boolean accountCreated = accountService.registerAccount(customerID, accountType, initialDeposit);
                    if(accountCreated){
                        System.out.println("Account created Successfully" + idGenerator.getAccountId());
                    }
                    else {
                        System.out.println("Unable to create Account. Try Again");
                    }
                    break;

                case 3:
                    //Deposit code
                    System.out.println("Enter Account ID:");
                    int accountId = scanner.nextInt();

                    System.out.println("Enter Amount:");
                    int amount = scanner.nextInt();

                    boolean deposited = accountService.deposit(accountId, amount);

                    if(deposited){
                        System.out.println("Deposit Successful. Current Balance: " +
                                accountRepository.findAccountById(accountId).getCurrentBalance());
                    }else{
                        System.out.println("Deposit Failed");
                    }
                    break;

                case 4:
                    //Withdraw code
                    System.out.println("Enter Account ID:");
                    accountId = scanner.nextInt();

                    System.out.println("Enter amount:");
                    amount = scanner.nextInt();

                    boolean withdrawal = accountService.withdraw(accountId, amount);

                    if(withdrawal){
                        System.out.println("Withdraw Successful. Current balance: " +
                                accountRepository.findAccountById(accountId).getCurrentBalance());
                    }else{
                        System.out.println("Withdraw Failed");
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

                    boolean transferred = accountService.transfer(senderAccountId, receiverAccountId, transferredAmount);

                    if(transferred){
                        System.out.println("Transfer Successful");
                    }else{
                        System.out.println("Transfer Failed");
                    }
                    break;

                case 6:
                    // View Transaction History
                    System.out.println("Enter AccountID");
                    accountId = scanner.nextInt();
                    List<Transaction> transactions = accountService.viewTransactionHistory(accountId);
                    if(transactions != null){
                        for(Transaction transaction : transactions){
                            System.out.println(transaction);
                        }
                    }
                    else{
                        System.out.println("Invalid AccountID");
                    }
                    break;

                case 7:
                    System.out.println("Enter AccountID");
                    accountId = scanner.nextInt();
                    Account account = accountService.viewAccountDetails(accountId);
                    if(account != null){
                        System.out.println(account);
                    }
                    else{
                        System.out.println("Invalid Account ID");
                    }
                case 8:
                    System.out.println("Thank you for using Bank Management System.");
                    keepRunning = false;
                    break;

                default:
                    System.out.println("Invalid Choice");
            }

        }while (keepRunning);
    }
}