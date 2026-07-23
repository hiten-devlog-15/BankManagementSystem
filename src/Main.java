import com.hiten.bankmanagementsystem.repository.AccountRepository;
import com.hiten.bankmanagementsystem.repository.CustomerRepository;
import com.hiten.bankmanagementsystem.service.AccountService;
import com.hiten.bankmanagementsystem.service.CustomerService;
import com.hiten.bankmanagementsystem.util.IdGenerator;
import com.hiten.bankmanagementsystem.validator.Validator;


import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        CustomerRepository customerRepository = new CustomerRepository();
        IdGenerator idGenerator = new IdGenerator();
        Validator validator = new Validator();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Name");
        String name = scanner.nextLine();

        System.out.println("Enter Phone Number");
        String phoneNumber = scanner.next();

        System.out.println("Enter Email");
        String email = scanner.next();

        System.out.println("Enter password");
        String password = scanner.next();

        CustomerService customerService = new CustomerService(customerRepository, idGenerator, validator);

        boolean registered = customerService.registerCustomer(name, phoneNumber, email, password);

        if (registered) {
            System.out.println("Customer registered successfully. Your CustomerID is " + idGenerator.getCustomerId());
        } else {
            System.out.println("Invalid email.");
        }


        System.out.println("Enter Customer ID");
        int customerID = scanner.nextInt();

        System.out.println("Enter Account Type: SAVINGS OR CURRENT");
        String accountType = scanner.next();

        System.out.println("Enter Initial Deposit");
        int initialDeposit = scanner.nextInt();

        AccountRepository accountRepository = new AccountRepository();
        AccountService accountService =new AccountService(customerRepository, accountRepository, idGenerator, validator);

        boolean accountCreated = accountService.createAccount(customerID, accountType, initialDeposit);
        if(accountCreated){
            System.out.println("Account created Successfully" + idGenerator.getAccountId());
        }
        else {
            System.out.println("Unable to create Account. Try Again");
        }



        //Deposit code
        System.out.println("Enter Account ID:");
        int accountId = scanner.nextInt();

        System.out.println("Enter Amount:");
        int amount = scanner.nextInt();

        accountService.deposit(accountId, amount);

        //Withdraw code
        System.out.println("Enter Account ID:");
        accountId = scanner.nextInt();

        System.out.println("Enter amount:");
        amount = scanner.nextInt();

        accountService.withdraw(accountId, amount);


    }
}