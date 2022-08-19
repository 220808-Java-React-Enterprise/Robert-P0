package com.revature.exotic_jerky.ui;

import com.revature.exotic_jerky.daos.CustomerDAO;
import com.revature.exotic_jerky.daos.StoreDAO;
import com.revature.exotic_jerky.models.Customer;
import com.revature.exotic_jerky.services.CustomerService;
import com.revature.exotic_jerky.services.StoreService;
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

                if (!customerService.isDuplicateEmail(email)){
                    System.out.println("\nUser doesn't exist");
                    System.out.println("\nWould you like to sign up? [Y]es/[N]o");
                    exitSignUp:{
                        while(true){
                            switch (input.nextLine().toUpperCase()){
                                case "Y": new SignUpMenu(new CustomerService(new CustomerDAO()), new StoreService(new StoreDAO())).start(); break exitSignUp;
                                case "N": new MainMenu(new CustomerService(new CustomerDAO())).start(); break exitSignUp;
                                default:
                                    System.out.println("\nInvalid Entry.");
                            }
                        }
                    }
                }

                try{
                    Customer customer = customerService.login(email, password);
                    if (customer.getRole().equals("ADMIN")) new AdminMenu(customer, new StoreService(new StoreDAO()), new CustomerService(new CustomerDAO())).start();
                    else
                        new MainMenu(new CustomerService(new CustomerDAO())).start(customer, true);
                    break exit;
                } catch (InvalidCustomerException e){
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
