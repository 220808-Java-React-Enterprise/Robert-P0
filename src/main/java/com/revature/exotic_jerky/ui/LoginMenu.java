package com.revature.exotic_jerky.ui;

import com.revature.exotic_jerky.daos.CustomerDAO;
import com.revature.exotic_jerky.models.Customer;
import com.revature.exotic_jerky.services.CustomerService;
import com.revature.exotic_jerky.utils.custom_exceptions.InvalidCustomerException;
import com.revature.exotic_jerky.utils.custom_exceptions.InvalidSQLException;

import java.util.Scanner;

public class LoginMenu implements IMenu{
    private final CustomerService customerService;

    Scanner input = new Scanner(System.in);

    // Pre: None
    // Post: A instance of CustomerService is instantiated
    // Purpose: To instantiate an instance of CustomerService
    public LoginMenu(CustomerService customer) {
        this.customerService = customer;
    }

    // Pre: Login is selected from the MainMenu
    // Post: A customer is logged in if account exists
    // Purpose: To log in to a customers account
    @Override
    public void start() {
        System.out.println("\nLets login!");
        String email, password;

        exit:{
            while (true){
                System.out.print("\nEmail: ");
                email = input.nextLine();

                signUpExit:{
                    if (!customerService.isDuplicateEmail(email)) {
                        System.out.println("\nEmail doesn't exist!");
                        System.out.println("Would you like to sign up? [Y]es/[N]o/[M]ain Menu");

                        while (true){
                            System.out.print("\nEnter: ");

                            switch (input.nextLine().toUpperCase()){
                                case "Y": new SignUpMenu(new CustomerService(new CustomerDAO())).start(); break exit;
                                case "N": break signUpExit;
                                case "M": new MainMenu().start(); break exit;
                                default:
                                    System.out.println("\nInvalid input. Try again...");
                            }
                        }
                    }

                    System.out.print("\nPassword: ");
                    password = input.nextLine();

                    try{
                        Customer customer = customerService.login(email, password);
                        if (customer.getRole().equals("ADMIN")) new AdminMenu(customer, new CustomerService(new CustomerDAO())).start();
                        else new ProductMenu(customer, new CustomerService(new CustomerDAO())).start();
                        break exit;
                    } catch (InvalidCustomerException e){
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
    }
}
