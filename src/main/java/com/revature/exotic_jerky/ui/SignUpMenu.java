package com.revature.exotic_jerky.ui;

import com.revature.exotic_jerky.daos.CustomerDAO;
import com.revature.exotic_jerky.daos.StoreDAO;
import com.revature.exotic_jerky.models.Customer;
import com.revature.exotic_jerky.models.Store;
import com.revature.exotic_jerky.models.UpdateAccount;
import com.revature.exotic_jerky.services.CustomerService;
import com.revature.exotic_jerky.services.StoreService;

import java.util.Scanner;
import java.util.UUID;

public class SignUpMenu extends UpdateAccount implements IMenu{
    private final CustomerService customerService;
    private final StoreService storeService;
    Scanner input = new Scanner(System.in);

    // Pre: None
    // Post: A instance of the SignUpMenu is instantiated
    // Purpose: To instantiate a SignUpMenu
    public SignUpMenu(CustomerService customer, StoreService storeService) {
        super(customer);
        this.customerService = customer;
        this.storeService = storeService;
    }

    // Pre: A new instance of SignUpMenu is called
    // Post: A customer has signed up or canceled sign up
    // Purpose: To sign up a customer to the database
    @Override
    public void start() {
        System.out.println("Sign Up! Or [M]enu anytime");

        Customer customer = signUp();

        exit:{
            if (customer == null) break exit;
            while (true){
                System.out.println("\nFirst Name: " + customer.getfName());
                System.out.println("Email: " + customer.getEmail());
                System.out.println("Please confirm account Sign up!");
                System.out.println("[Y]es/[N]o/[U]pdate");
                System.out.print("\nEnter: ");

                switch(input.nextLine().toUpperCase()){
                    case "Y":
                        String email = customer.getEmail();

                        if (email.substring(email.indexOf("@")).equals("@exoticjerky.com"))
                            if (customer.getPassword().equals("admin213")){
                                customer.setRole("ADMIN");
                                customer.setfName(fName()); customer.setAddress(address());
                                customer.setCity(city()); customer.setState(state());
                                customer.setZip(zip()); customer.setPhone(phone());

                                if (!storeService.isDuplicateStore(customer.getAddress())){
                                    Store store = new Store(UUID.randomUUID().toString(), "Exotic Jerky", customer.getEmail(), customer.getAddress(),
                                            customer.getCity(), customer.getState(), customer.getZip(), customer.getPhone());

                                    customerService.signUp(customer);
                                    storeService.saveStore(store);
                                    new AdminMenu(customer, store, new StoreService(new StoreDAO()), new CustomerService(new CustomerDAO())).start();
                                } break exit;
                            }
                        customerService.signUp(customer);
                        new MainMenu(new CustomerService(new CustomerDAO()), storeService).start(customer, true);
                        break exit;
                    case "N":
                        new MainMenu(new CustomerService(new CustomerDAO()), storeService).start();
                        break exit;
                    case "U":
                        updateExit:{
                            System.out.println("\nWhat would you like to update!");
                            System.out.println("[F]irst Name");
                            System.out.println("[E]mail");
                            System.out.println("[P]assword");

                            while (true){
                                System.out.print("\nEnter: ");
                                switch (input.nextLine().toUpperCase()){
                                    case "F": customer.setfName(fName());
                                        System.out.println("\nFirst name updated"); break updateExit;
                                    case "E": customer.setEmail(email());
                                        System.out.println("\nEmail updated"); break updateExit;
                                    case "P": customer.setPassword(pass());
                                        System.out.println("\nLast name updated"); break updateExit;
                                    default:
                                        System.out.println("\nInvalid entry! Try Again...");
                                }
                            }
                        } break;
                    default:
                        System.out.println("\nInvalid entry! Try Again...");
                        break;
                }
            }
        }
    }

    // Pre:
    // Post:
    // Purpose:
    public void start(Customer newCustomer){
        System.out.println("\nSign Up!");

        Customer customer = signUp();

        customer.setId(newCustomer.getId());

        exit:{
            while (true){
                System.out.println("\nFirst Name: " + customer.getfName());
                System.out.println("Email: " + customer.getEmail());
                System.out.println("Please confirm account Sign up!");
                System.out.println("[Y]es/[N]o/[U]pdate");
                System.out.print("\nEnter: ");

                switch(input.nextLine().toUpperCase()){
                    case "Y":
                        customerService.updateAccount(customer);
                        break exit;
                    case "N":
                        break exit;
                    case "U":
                        updateExit:{
                            System.out.println("\nWhat would you like to update!");
                            System.out.println("[F]irst Name");
                            System.out.println("[E]mail");
                            System.out.println("[P]assword");

                            while (true){
                                System.out.print("\nEnter: ");
                                switch (input.nextLine().toUpperCase()){
                                    case "F": customer.setfName(fName());
                                        System.out.println("\nFirst name updated"); break updateExit;
                                    case "E": customer.setEmail(email());
                                        System.out.println("\nEmail updated"); break updateExit;
                                    case "P": customer.setPassword(pass());
                                        System.out.println("\nLast name updated"); break updateExit;
                                    default:
                                        System.out.println("\nInvalid entry! Try Again...");
                                }
                            }
                        } break;
                    default:
                        System.out.println("\nInvalid entry! Try Again...");
                        break;
                }
            }
        }
    }

    // Pre: start() is called
    // Post: A customer is returned, null if user cancels
    // Purpose: To verify between signup steps if user wants to cancel
    public Customer signUp(){
        String[] inputs = new String[10];
        exit:{
            for (int i = 0; i <= 2; i++){
                switch (i){
                    case 0: inputs[0] = fName(); break;
                    case 1: inputs[1] = email(); break;
                    case 2: inputs[2] = pass(); break;
                }
                if (inputs[i].equalsIgnoreCase("M")){
                    new MainMenu(new CustomerService(new CustomerDAO()), storeService).start();
                    return null;
                }
            }
        }
        return new Customer(inputs[0], inputs[1], inputs[2]);
    }
}