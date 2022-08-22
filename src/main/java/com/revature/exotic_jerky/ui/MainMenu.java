package com.revature.exotic_jerky.ui;

import com.revature.exotic_jerky.daos.CartDAO;
import com.revature.exotic_jerky.daos.CustomerDAO;
import com.revature.exotic_jerky.daos.ProductDAO;
import com.revature.exotic_jerky.daos.StoreDAO;
import com.revature.exotic_jerky.models.Customer;
import com.revature.exotic_jerky.models.UpdateAccount;
import com.revature.exotic_jerky.services.CartService;
import com.revature.exotic_jerky.services.CustomerService;
import com.revature.exotic_jerky.services.ProductService;
import com.revature.exotic_jerky.services.StoreService;

import java.util.Scanner;

public class MainMenu extends UpdateAccount implements IMenu{
    private final StoreService storeService;
    Scanner scan = new Scanner(System.in);

    public MainMenu(CustomerService customerService, StoreService storeService) {
        super(customerService);
        this.storeService = storeService;
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
                System.out.println("[C]art");
                System.out.println("[L]ogin");
                System.out.println("[S]ign Up");
                System.out.println("E[x]it");

                System.out.print("\nEnter: ");

                switch (scan.nextLine().toUpperCase()){
                    case "P": new ProductMenu(new Customer(), new CustomerService(new CustomerDAO()), new ProductService(new ProductDAO()), new CartService(new CartDAO()), false).start(); break exit;
                    case "C": new CartMenu(new Customer(), new CartService(new CartDAO()), false).start(); break exit;
                    case "L": new LoginMenu(new CustomerService(new CustomerDAO()), new StoreService(new StoreDAO())).start(); break exit;
                    case "S": new SignUpMenu(new CustomerService(new CustomerDAO()), new StoreService(new StoreDAO())).start(); break exit;
                    case "X":
                        break exit;
                }
            }
        }
        System.out.println("\nThanks for Visiting! Hope to see you again soon!");
    }

    // Pre: A customer is signed in
    // Post: The start menu for a signed in customer is printed out
    // Purpose: To bring customer back to the MainMenu
    public void start(Customer customer, Boolean loggedIn) {
        exit:{
            while (true){
                String input;
                System.out.print("\nWelcome to Exotic Jerky");

                if(loggedIn) System.out.println(" " + customer.getfName() + "!");
                else System.out.println("!");

                menuExit:
                {
                    while (true){
                        if (customer.getRole().equals("ADMIN"))
                            System.out.println("[A]dmin Menu");
                        else
                            System.out.println("[P]roduct");

                        if (loggedIn){
                            System.out.println("[S]ign Out");
                            System.out.println("[U]pdate Account");
                        }
                        else{
                            System.out.println("[C]art");
                            System.out.println("[L]ogin");
                            System.out.println("[S]ign Up");
                        }
                        System.out.println("E[x]it Store");

                        System.out.print("\nEnter: ");

                        switch (scan.nextLine().toUpperCase()){
                            case "A": if (customer.getRole().equals("ADMIN"))
                                new AdminMenu(customer, storeService.getByEmail(customer.getEmail()), new StoreService(new StoreDAO()), new CustomerService(new CustomerDAO())).start();
                            case "P": new ProductMenu(customer, new CustomerService(new CustomerDAO()), new ProductService(new ProductDAO()), new CartService(new CartDAO()), loggedIn).start(); break exit;
                            case "C": new CartMenu(customer, new CartService(new CartDAO()), loggedIn); break exit;
                            case "S":
                                if (loggedIn) new MainMenu(new CustomerService(new CustomerDAO()), storeService).start();
                                else new SignUpMenu(new CustomerService(new CustomerDAO()), storeService).start(customer);
                                break exit;
                            case "L":
                                if (!loggedIn) new LoginMenu(new CustomerService(new CustomerDAO()), storeService).start();
                                else { System.out.println("\nInvalid input. Try Again..."); break; }
                                break exit;
                            case "U":
                                if (loggedIn) { customer = updateInfo(customer);
                                    System.out.println("\nAccount information updated!");break exit;}
                                else { System.out.println("\nInvalid input. Try Again..."); break; }
                            case "X":
                                break exit;
                            default:
                                System.out.println("\nAccount information updated!"); break;
                        }
                    }
                }
            }
        }
    }
}
