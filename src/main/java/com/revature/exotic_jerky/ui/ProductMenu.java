package com.revature.exotic_jerky.ui;

import com.revature.exotic_jerky.daos.CartDAO;
import com.revature.exotic_jerky.models.Cart;
import com.revature.exotic_jerky.models.Customer;
import com.revature.exotic_jerky.services.CartService;
import com.revature.exotic_jerky.services.CustomerService;
import com.revature.exotic_jerky.utils.custom_exceptions.InvalidCartException;

import java.util.Scanner;

public class ProductMenu implements IMenu{
    private final Customer customer;
    private final CustomerService customerService;
    private final CartService cartService;

    Scanner input = new Scanner(System.in);
    private Cart cart;

    private final boolean isLoggedIn;

    public ProductMenu(Customer customer, CustomerService customerService, CartService cartService, boolean loggedIn) {
        this.customer = customer;
        this.customerService = customerService;
        this.cartService = cartService;
        this.isLoggedIn = loggedIn;
    }

    @Override
    public void start() {
        if (isLoggedIn) {
            System.out.println("Welcome " + customer.getfName() + "!");
            cart = cartService.hasExistingCart(customer.getId());
        }
        else
            System.out.println("Welcome to our Product!");

        System.out.println("\nShop by...");
        System.out.println("[S]picy");
        System.out.println("S[w]eet");
        System.out.println("[O]riginal");
        System.out.println("[M]ain Menu");
        System.out.println("E[x]it Store");

        exit:{
            while (true){
                String category = input.nextLine().toUpperCase();

                if (category.equals("S")){

                }
                else if (category.equals("W")){

                }
                else if (category.equals("O")){

                }
                else if (category.equals("M")){

                }
                else if (category.equals("X")){

                }
            }
        }


    }
}
