package com.revature.exotic_jerky.ui;

import com.revature.exotic_jerky.daos.CartDAO;
import com.revature.exotic_jerky.daos.CustomerDAO;
import com.revature.exotic_jerky.daos.ProductDAO;
import com.revature.exotic_jerky.daos.StoreDAO;
import com.revature.exotic_jerky.models.Cart;
import com.revature.exotic_jerky.models.Customer;
import com.revature.exotic_jerky.services.CartService;
import com.revature.exotic_jerky.services.CustomerService;
import com.revature.exotic_jerky.services.ProductService;
import com.revature.exotic_jerky.services.StoreService;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CartMenu implements IMenu{
    private final Customer customer;
    private final CartService cartService;
    private final Cart cart;
    private boolean isLoggedIn;

    Scanner input = new Scanner(System.in);

    public CartMenu(Customer customer, CartService cartService, boolean isLoggedIn) {
        this.customer = customer;
        this.cartService = cartService;
        this.cart = cartService.hasExistingCart(customer.getId());
        this.isLoggedIn = isLoggedIn;
    }

    @Override
    public void start() {
        Map<String, List<String>> summary;
        exit:{
            while (true){
                System.out.print("\nWelcome to your cart");

                if (isLoggedIn) System.out.println(" " + customer.getfName() + "!");
                else System.out.println("!");

                System.out.println("\nSummary:");
                summary = getCart(cart.getId());

                if (summary.size() == 0){
                    System.out.println("\nCart is empty! :(");
                    System.out.println("\nWould you like to check our products? [Y]es/[N]o");
                    System.out.print("Enter: ");

                    while (true){
                        switch(input.nextLine().toUpperCase()){
                            case "Y": new ProductMenu(new Customer(), new CustomerService(new CustomerDAO()), new ProductService(new ProductDAO()), new CartService(new CartDAO()), isLoggedIn).start(); break exit;
                            case "N": break exit;
                            default:
                                System.out.println("\nInvalid Entry! Try Again...");
                        }
                    }
                }

                System.out.println("\nWhat would you like to do?");
                System.out.println("\n[U]pdate Cart");
                System.out.println("[C]heck Out");
                System.out.println("[B]ack to Products");

                exitChoice:{
                    while (true){
                        System.out.print("\nEnter: ");

                        switch(input.nextLine().toUpperCase()){
                            case "U":
                            case "C":
                            case "B":
                        }
                    }
                }

            }
        }
    }

    private Map<String, List<String>> getCart(String customerID) {
        Map<String, List<String>> map = cartService.getCheckOutCart(cart.getCustomerID());
        int index = 1, maxStrLength = 30;
        float grandTotal = 0.00f;

        for (List<String> details : map.values()) {
            // Output selection character and name
            System.out.print("\n[" + index + "] " + details.get(0));

            maxStrLength -= details.get(0).length() + details.get(1).length() + 1;

            for (int i = 0; i < maxStrLength; i++)
                System.out.print("-");

            System.out.println("$" + details.get(1));

            System.out.print("\tQuantity");

            maxStrLength = 30 - "\tQuantity".length() - details.get(2).length() - 2;

            for (int i = 0; i < maxStrLength; i++)
                System.out.print("-");

            System.out.println(details.get(2));

            System.out.print("\tTotal");

            maxStrLength = 30 - "\tTotal".length() - details.get(3).length() + 1;

            for (int i = 0; i < maxStrLength; i++)
                System.out.print("-");

            System.out.println("$" + details.get(3));

            grandTotal += Float.parseFloat(details.get(3));
            maxStrLength = 30;
            index++;
        }

        if (map.size() != 0){
            System.out.print("\n\tGrand Total");

            maxStrLength -= "\tGrand Total".length() + String.valueOf(grandTotal).length() - 6;

            for (int i = 0; i < maxStrLength; i++)
                System.out.print("-");

            System.out.println("$" + String.format("%.2f", grandTotal));
        }

        return map;
    }

    private void checkOut(){

    }
}
