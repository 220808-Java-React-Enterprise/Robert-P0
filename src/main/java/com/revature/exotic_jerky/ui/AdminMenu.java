package com.revature.exotic_jerky.ui;

import com.revature.exotic_jerky.daos.ProductDAO;
import com.revature.exotic_jerky.models.Customer;
import com.revature.exotic_jerky.models.Store;
import com.revature.exotic_jerky.services.CustomerService;
import com.revature.exotic_jerky.services.OrderService;
import com.revature.exotic_jerky.services.ProductService;
import com.revature.exotic_jerky.services.StoreService;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class AdminMenu implements IMenu{
    private  final Customer customer;
    private final StoreService storeService;
    private final CustomerService customerService;
    private final OrderService orderService;
    private final Store store;

    Scanner input = new Scanner(System.in);

    // Non-Default Constructor
    public AdminMenu(Customer customer, Store store, StoreService storeService, CustomerService customerService, OrderService orderService) {
        this.customer = customer;
        this.storeService = storeService;
        this.store = store;
        this.customerService = customerService;
        this.orderService = orderService;
    }

    // Pre:
    // Post:
    // Purpose:
    @Override
    public void start() {
        Customer customerSearched;
        Map<String, List<String>> orders;
        exitAdmin:{
            while (true){
                System.out.println("\nAdmin Menu");

                System.out.println("\nWhat would you like to do?");
                System.out.println("[I]nventory");
                System.out.println("[H]istory of Store Orders");
                System.out.println("[S]earch Customer");
                System.out.println("[M]ain Menu");
                System.out.println("E[x]it");

                exit:{
                    while (true){
                        System.out.print("\nEnter: ");

                        switch(input.nextLine().toUpperCase()){
                            case "I": new InventoryMenu(store, new ProductService(new ProductDAO())).start(); break exit;
                            case "H":
                                orders = getOrderList(customer, true);
                                if (orders.size() != 0){
                                    printOrderHistory(orders);
                                    Map.Entry<String, List<String>> entry = orders.entrySet().iterator().next();
                                    expandOrder(orders);
                                }
                                else
                                    System.out.println("\nNo orders located");
                                break exit;
                            case "S":
                                customerSearched = searchCustomer();
                                if (customerSearched == null) { System.out.println("\n No customer found"); break exitAdmin; }

                                orders = getOrderList(customerSearched, false);
                                if (orders == null){ System.out.println("No orders found"); break exitAdmin; }

                                printOrderHistory(orders);
                                expandOrder(orders);
                                break exit;

                            case "M": new MainMenu(customerService, storeService, orderService).start(customer, true); break exitAdmin;
                            case "X": System.out.println("\nThanks for Visiting! Hope to see you again soon!"); System.exit(0);
                            default:
                                System.out.println("\nInvalid Entry! Try Again...");
                        }
                    }
                }
            }
        }
    }

    // Pre:
    // Post:
    // Purpose:
    private Customer searchCustomer(){
        while (true){
            System.out.println("\nWhat is the customers email? Or [B]ack");
            System.out.print("\nEnter: ");
            String email = input.nextLine();

            if (email.equalsIgnoreCase("B")) return null;

            if (!customerService.isDuplicateEmail(email)){
                System.out.println("\nUser doesn't exits");
            }
            else{
                return customerService.getByEmail(email);
            }
        }
    }

    // Pre: User requests their order history
    // Post: A list of the users order history is returned
    // Purpose: To get the order history of a customer
    private Map<String, List<String>> getOrderList(Customer customer, boolean admin){
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

            String id = customer.getId();
            String role = customer.getRole();

            if (admin){
                id = store.getId();
                role = this.customer.getRole();
            }

            return orderService.getOrderHistory(id, sortType, ascending, role);

        }
        return null;
    }

    // Pre: The user is signed in
    // Post: The users order history is displayed to the user
    // Purpose: To get the user order history and print it to the user
    private void printOrderHistory(Map<String, List<String>> orders){
        int index = 1, maxStrLength;

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
            System.out.println("\nNo order history. :(");
        }
    }

    // Pre: The order history of a user is displayed to the screen
    // Post: A specific order in the customers orders is expanded
    // Purpose: To show all details of a customers order
    private void expandOrder(Map<String, List<String>> map){
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
                            printFullOrder(order);
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
    private void printFullOrder(List<String> order){
        Map<String, List<String>> fullOrder = orderService.getOrderHistory(order.get(5));
        float grandTotal = 0;

        System.out.println("\nOrder ID: " + order.get(5));

        for (List<String> product : fullOrder.values()){
            System.out.println("\n" + product.get(0));
            System.out.print("Quantity");

            int maxSpace = 34 - "Quantity".length() - product.get(1).length();

            for (int i = 0; i < maxSpace; i++)
                System.out.print("-");

            System.out.println(product.get(1));
            System.out.print("Total");
            maxSpace = 30 - "Total".length() - product.get(2).length();

            for (int i = 0; i < maxSpace; i++)
                System.out.print("-");

            System.out.printf("%.2f%n", Float.parseFloat(product.get(2)));
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
