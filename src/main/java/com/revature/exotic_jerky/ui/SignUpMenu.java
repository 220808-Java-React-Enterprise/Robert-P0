package com.revature.exotic_jerky.ui;

import com.revature.exotic_jerky.daos.CustomerDAO;
import com.revature.exotic_jerky.models.Customer;
import com.revature.exotic_jerky.models.UpdateAccount;
import com.revature.exotic_jerky.services.CustomerService;

import java.util.Scanner;
import java.util.UUID;

public class SignUpMenu extends UpdateAccount implements IMenu{
    private final CustomerService customerService;
    Scanner input = new Scanner(System.in);

    // Pre: None
    // Post: A instance of the SignUpMenu is instantiated
    // Purpose: To instantiate a SignUpMenu
    public SignUpMenu(CustomerService customer) {
        super(customer);
        this.customerService = customer;
    }

    // Pre: A new instance of SignUpMenu is called
    // Post: A customer has signed up or canceled sign up
    // Purpose: To sign up a customer to the database
    @Override
    public void start() {
        System.out.println("Sign Up!");

        Customer customer = new Customer(UUID.randomUUID().toString(),
                fName(), lName(), email(), pass(), address(), city(), state(), zip(), phone());

        Exit:{
            while (true){
                printSummaryOfCustomer(customer);

                System.out.println("\nPlease confirm account Sign up " + customer.getfName() + "!");
                System.out.println("[Y]es");
                System.out.println("[N]o");
                System.out.println("[U]pdate");
                System.out.print("\nEnter: ");

                switch(input.nextLine().toUpperCase()){
                    case "Y":
                        customerService.signUp(customer);
                        new MainMenu(new CustomerService(new CustomerDAO())).start(customer, true);
                        break Exit;
                    case "N":
                        new MainMenu(new CustomerService(new CustomerDAO())).start();
                        break Exit;
                    case "U":
                        customer = updateInfo(customer);
                    default:
                        System.out.println("\nInvalid entry! Try Again...");
                        break;
                }
            }
        }
    }
}