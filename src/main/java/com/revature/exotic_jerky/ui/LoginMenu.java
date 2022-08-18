package com.revature.exotic_jerky.ui;

import com.revature.exotic_jerky.daos.CartDAO;
import com.revature.exotic_jerky.daos.CustomerDAO;
import com.revature.exotic_jerky.models.Customer;
import com.revature.exotic_jerky.services.CartService;
import com.revature.exotic_jerky.services.CustomerService;
import com.revature.exotic_jerky.utils.custom_exceptions.InvalidCustomerException;

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
        System.out.println("\nLets login! Or [M]ain Menu");
        String email, password;

        exit:{
            while (true){
                System.out.print("\nEmail: ");
                email = input.nextLine();

                if (email.equalsIgnoreCase("M")){
                    new MainMenu(new CustomerService(new CustomerDAO())).start(); break exit;
                }

                System.out.print("\nPassword: ");
                password = input.nextLine();

                if (email.equalsIgnoreCase("M")){
                    new MainMenu(new CustomerService(new CustomerDAO())).start(); break exit;
                }

                try{
                    Customer customer = customerService.login(email, password);
                    if (customer.getRole().equals("ADMIN")) new AdminMenu(customer, new CustomerService(new CustomerDAO())).start();
                    else new MainMenu(new CustomerService(new CustomerDAO())).start(customer, true);
                    break exit;
                } catch (InvalidCustomerException e){
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
