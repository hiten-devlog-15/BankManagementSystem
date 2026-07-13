import com.hiten.bankmanagementsystem.repository.CustomerRepository;
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
        String name = scanner.next();

        System.out.println("Enter Phone Number");
        String phoneNumber = scanner.next();

        System.out.println("Enter Email");
        String email = scanner.next();

        System.out.println("Enter password");
        String password = scanner.next();

        CustomerService customerService = new CustomerService(customerRepository, idGenerator, validator);
        customerService.registerCustomer(name, phoneNumber, email, password);

        boolean registered = customerService.registerCustomer(
                name,
                phoneNumber,
                email,
                password
        );

        if (registered) {
            System.out.println("Customer registered successfully.");
        } else {
            System.out.println("Invalid email.");
        }

    }
}