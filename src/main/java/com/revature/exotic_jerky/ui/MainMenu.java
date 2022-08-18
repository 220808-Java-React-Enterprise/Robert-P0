package com.revature.exotic_jerky.ui;

import com.revature.exotic_jerky.daos.CartDAO;
import com.revature.exotic_jerky.daos.CustomerDAO;
import com.revature.exotic_jerky.daos.ProductDAO;
import com.revature.exotic_jerky.models.Customer;
import com.revature.exotic_jerky.models.UpdateAccount;
import com.revature.exotic_jerky.services.CartService;
import com.revature.exotic_jerky.services.CustomerService;
import com.revature.exotic_jerky.services.ProductService;

import java.util.Scanner;

public class MainMenu extends UpdateAccount implements IMenu{
    Scanner scan = new Scanner(System.in);

    public MainMenu(CustomerService customerService) {
        super(customerService);
    }

    // Pre: None
    // Post: The start menu is printed to screen
    // Purpose: To start the MainMenu
    @Override
    public void start() {
        String input;
        System.out.println("\nWelcome to Exotic Jerky!");

        exit:
        {
            while (true){
                System.out.println("[P]roduct");
                System.out.println("[L]ogin");
                System.out.println("[S]ign Up");
                System.out.println("E[x]it");

                System.out.print("\nEnter: ");

                switch (scan.nextLine().toUpperCase()){
                    case "P": new ProductMenu(new Customer(), new CustomerService(new CustomerDAO()), new ProductService(new ProductDAO()), new CartService(new CartDAO()), false).start(); break exit;
                    case "L": new LoginMenu(new CustomerService(new CustomerDAO())).start(); break exit;
                    case "S": new SignUpMenu(new CustomerService(new CustomerDAO())).start(); break exit;
                    case "X":
                        System.out.println("Thanks for Visiting! Hope to see you again!"); break exit;
                }
            }
        }
    }

    // Pre: A customer is signed in
    // Post: The start menu for a signed in customer is printed out
    // Purpose: To bring customer back to the MainMenu
    public void start(Customer customer, Boolean loggedIn) {
        exit:{
            while (true){
                String input;
                System.out.println("\nWelcome " + customer.getfName() + " to Exotic Jerky!");

                menuExit:
                {
                    while (true){
                        System.out.println("[P]roduct");
                        System.out.println("[S]ign Out");
                        System.out.println("[U]pdate Account");
                        System.out.println("E[x]it Store");

                        System.out.print("\nEnter: ");

                        switch (scan.nextLine().toUpperCase()){
                            case "P": new ProductMenu(customer, new CustomerService(new CustomerDAO()), new ProductService(new ProductDAO()), new CartService(new CartDAO()), true).start(); break;
                            case "S": new MainMenu(new CustomerService(new CustomerDAO())).start(); break menuExit;
                            case "U": updateInfo(customer);
                                System.out.println("\nAccount information updated!");break menuExit;
                            case "X":
                                System.out.println("Thanks for Visiting! Hope to see you again!"); break exit;
                        }
                    }
                }
            }
        }
    }
}
