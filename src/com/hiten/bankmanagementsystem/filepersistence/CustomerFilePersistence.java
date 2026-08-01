package com.hiten.bankmanagementsystem.filepersistence;

import com.hiten.bankmanagementsystem.model.Customer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CustomerFilePersistence {

    private static final String FILE_PATH = "customers.txt";

    public void saveCustomer(Customer customer) {
        String line = customer.getCustomerId() + "," +
                customer.getName() + "," +
                customer.getPhoneNumber() + "," +
                customer.getEmail() + "," +
                customer.getPassword() + "," +
                customer.getCreatedAt();

        try (FileWriter fileWriter = new FileWriter(FILE_PATH, true)) {
            fileWriter.write(line);
            fileWriter.write(System.lineSeparator());
        } catch (IOException e) {
            throw new RuntimeException("Unable to save customer.", e);
        }
    }

    public List<Customer> loadCustomers() {
        List<Customer> customerList = new ArrayList<>();
        File file = new File(FILE_PATH);
        // First run: file doesn't exist yet
        if (!file.exists()) {
            return customerList;
        }
        try (
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader)
        ) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] customerData = line.split(",");
                int customerId = Integer.parseInt(customerData[0]);
                String name = customerData[1];
                String phoneNumber = customerData[2];
                String email = customerData[3];
                String password = customerData[4];
                LocalDate createdAt = LocalDate.parse(customerData[5]);
                Customer customer = new Customer(customerId, name, phoneNumber, email, password, createdAt
                );
                customerList.add(customer);
            }
        } catch (IOException e) {
            throw new RuntimeException("Unable to load customers.", e);
        }
        return customerList;
    }
}