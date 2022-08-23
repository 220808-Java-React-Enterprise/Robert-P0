package com.revature.exotic_jerky.ui;

import com.revature.exotic_jerky.daos.CartDAO;
import com.revature.exotic_jerky.daos.CustomerDAO;
import com.revature.exotic_jerky.daos.ProductDAO;
import com.revature.exotic_jerky.daos.StoreDAO;
import com.revature.exotic_jerky.models.Customer;
import com.revature.exotic_jerky.models.Store;
import com.revature.exotic_jerky.models.UpdateAccount;
import com.revature.exotic_jerky.services.*;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MainMenu extends UpdateAccount implements IMenu{
    private final StoreService storeService;
    private final OrderService orderService;
    Scanner input = new Scanner(System.in);

    // Pre: None
    // Post: A instance of MainMenu is instantiated
    // Purpose: To give the user the MainMenu
    public MainMenu(CustomerService customerService, StoreService storeService, OrderService orderService) {
        super(customerService);
        this.storeService = storeService;
        this.orderService = orderService;
    }

    // Pre: None
    // Post: The start menu is printed to screen
    // Purpose: To start the MainMenu
    @Override
    public void start() {
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

                switch (input.nextLine().toUpperCase()){
                    case "P": new ProductMenu(new Customer(), new CustomerService(new CustomerDAO()), new ProductService(new ProductDAO()), new CartService(new CartDAO()), storeService, false).start(); break exit;
                    case "C": new CartMenu(new Customer(), new CartService(new CartDAO()), orderService, storeService, new ProductService(new ProductDAO()), new Store(), false).start(); break;
                    case "L": new LoginMenu(new CustomerService(new CustomerDAO()), new CartService(new CartDAO()), new StoreService(new StoreDAO())).start(); break exit;
                    case "S": new SignUpMenu(new CustomerService(new CustomerDAO()), new StoreService(new StoreDAO())).start(); break exit;
                    case "X":
                        break exit;
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
                System.out.print("\nWelcome to Exotic Jerky");

                if(loggedIn) System.out.println(" " + customer.getfName() + "!");
                else System.out.println("!");

                menuExit:
                {
                    while (true){
                        if (loggedIn){
                            if (customer.getRole().equals("ADMIN"))
                                System.out.println("[A]dmin Menu");
                            else {
                                System.out.println("[P]roduct");
                                System.out.println("[O]rder History");
                                System.out.println("[C]art");
                            }
                            System.out.println("[S]ign Out");
                            System.out.println("[U]pdate Account");
                        }
                        else{
                            System.out.println("[L]ogin");
                            System.out.println("[S]ign Up");
                        }
                        System.out.println("E[x]it Store");

                        System.out.print("\nEnter: ");

                        switch (input.nextLine().toUpperCase()){
                            case "A": if (customer.getRole().equals("ADMIN"))
                                { new AdminMenu(customer, storeService.getByEmail(customer.getEmail()), new StoreService(new StoreDAO()), new CustomerService(new CustomerDAO()), orderService).start(); break exit; }
                                else { System.out.println("\nInvalid input. Try Again..."); break; }

                            case "P": new ProductMenu(customer, new CustomerService(new CustomerDAO()), new ProductService(new ProductDAO()), new CartService(new CartDAO()), storeService, loggedIn).start(); break exit;

                            case "O": Map<String, List<String>> map = getOrderList(customer);
                                printOrderHistory(map);
                                expandOrder(customer, map); break menuExit;

                            case "C": new CartMenu(customer, new CartService(new CartDAO()), orderService, storeService, new ProductService(new ProductDAO()), new Store(), loggedIn).start(); break;

                            case "S":
                                if (loggedIn) new MainMenu(new CustomerService(new CustomerDAO()), storeService, orderService).start();
                                else new SignUpMenu(new CustomerService(new CustomerDAO()), storeService).start(customer, false);
                                break exit;

                            case "L":
                                if (!loggedIn) new LoginMenu(new CustomerService(new CustomerDAO()), new CartService(new CartDAO()), storeService).start();
                                else { System.out.println("\nInvalid input. Try Again..."); break; }
                                break exit;

                            case "U":
                                if (loggedIn) { customer = updateInfo(customer);
                                    System.out.println("\nAccount information updated!");break exit;}
                                else { System.out.println("\nInvalid input. Try Again..."); break; }

                            case "X":
                                break exit;
                            default:
                                System.out.println("\nInvalid input. Try Again..."); break;
                        }
                    }
                }
            }
        }
    }

    // Pre: User requests their order history
    // Post: A list of the users order history is returned
    // Purpose: To get the order history of a customer
    private Map<String, List<String>> getOrderList(Customer customer){
        String sortType, temp;
        boolean ascending;
        exitSearch:{
            exit:
            {
                while (true) {
                    System.out.println("\nWhat would you like to sort by?");
                    System.out.println("[D]ate");
                    System.out.println("[P]rice");
                    System.out.println("[C]ancel");
                    System.out.print("\nEnter: ");
                    temp = input.nextLine().toUpperCase();

                    switch (temp) {
                        case "D":
                            sortType = "created";
                            break exit;
                        case "P":
                            sortType = "grand_total";
                            break exit;
                        case "C":
                            break exitSearch;
                        default:
                            System.out.println("\nInvalid Entry! Try Again...");
                    }
                }
            }

            exit:{
                while (true){
                    System.out.println("\nAscending or Descending?");
                    System.out.println("[D]escending");
                    System.out.println("[A]scending");
                    System.out.println("[C]ancel");
                    System.out.print("\nEnter: ");
                    temp = input.nextLine().toUpperCase();

                    switch(temp){
                        case "D": ascending = false; break exit;
                        case "A": ascending = true; break exit;
                        case "C": break exitSearch;
                        default:
                            System.out.println("\nInvalid Entry! Try Again...");
                    }
                }
            }

            return orderService.getOrderHistory(customer.getRole(), customer.getId(), sortType, ascending);

        }
        return null;
    }

    // Pre: The user is signed in
    // Post: The users order history is displayed to the user
    // Purpose: To get the user order history and print it to the user
    private void printOrderHistory(Map<String, List<String>> orders){
        int index = 1, maxStrLength = 30;

        if (orders != null){
            for (List<String> details : orders.values()) {
                // Output selection character and name
                String id = details.get(5);
                String title = "[" + index + "] Order ID: ..." + id.substring(id.length() - 4);
                System.out.println("\n" + title);

                System.out.print("\tDate");

                maxStrLength = 40 - "\tDate".length() - details.get(3).length();

                for (int i = 0; i < maxStrLength; i++)
                    System.out.print("-");

                System.out.println(details.get(3).substring(0, 10));

                System.out.print("\tTotal");

                maxStrLength = 25 - "\tTotal".length() - details.get(4).length() - 2;

                for (int i = 0; i < maxStrLength; i++)
                    System.out.print("-");

                System.out.println("$" + String.format("%.2f", Float.parseFloat(details.get(4))));

                index++;
            }
        }
        else{
            System.out.println("\nNo order history for customer. :(");
        }
    }

    // Pre: The order history of a user is displayed to the screen
    // Post: A specific order in the customers orders is expanded
    // Purpose: To show all details of a customers order
    private void expandOrder(Customer customer, Map<String, List<String>> map){
        exit:{
            while (true){
                System.out.print("\nSelect to expand order. Or [B]ack");
                System.out.print("\nEnter: ");
                String userInput = input.nextLine();

                if (userInput.equalsIgnoreCase("B")) break exit;

                try{
                    int index = Integer.parseInt(userInput);

                    int counter = 1;
                    for (List<String> order : map.values()){
                        if (counter == index){
                            printFullOrder(customer, order);
                            printOrderHistory(map);
                            break;
                        }
                        counter++;
                    }

                } catch (NumberFormatException e){
                    System.out.println("\nInvalid Input. Must be a numeric value.");
                } catch (IndexOutOfBoundsException e){
                    System.out.println("\nInvalid Input. Must be a value between 1-" + map.size() + ".");
                }
            }
        }

    }

    // Pre: A customers has requested to expand an order in their history
    // Post: The expanded form of an order is printed
    // Purpose: To print the full order of an order in the customers history
    private void printFullOrder(Customer customer, List<String> order){
        Map<String, List<String>> fullOrder = orderService.getOrderHistory(customer.getId(), order.get(5));
        float grandTotal = 0;

        System.out.println("\nOrder ID: " + order.get(5));

        for (List<String> product : fullOrder.values()){
            System.out.println("\n" + product.get(0));
            System.out.print("Quantity");

            int maxSpace = 30 - "Quantity".length() - product.get(1).length();

            for (int i = 0; i < maxSpace; i++)
                System.out.print("-");

            System.out.println(product.get(1));
            System.out.print("Total");
            maxSpace = 30 - "Total".length() - product.get(2).length();

            for (int i = 0; i < maxSpace; i++)
                System.out.print("-");

            System.out.println(product.get(2));
            grandTotal = Float.parseFloat(product.get(3));
        }

        System.out.print("\nGrand Total");

        int maxSpace = 30 - "Total".length() - String.valueOf(grandTotal).length() - 2;

        for (int i = 0; i < maxSpace; i++)
            System.out.print("-");

        System.out.printf("%.2f%n", grandTotal);

        System.out.println("\nPress enter to continue");
        input.nextLine();
    }
}
