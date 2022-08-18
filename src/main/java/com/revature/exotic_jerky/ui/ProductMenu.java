package com.revature.exotic_jerky.ui;

import com.revature.exotic_jerky.daos.CustomerDAO;
import com.revature.exotic_jerky.models.Cart;
import com.revature.exotic_jerky.models.Customer;
import com.revature.exotic_jerky.models.Product;
import com.revature.exotic_jerky.services.CartService;
import com.revature.exotic_jerky.services.CustomerService;
import com.revature.exotic_jerky.services.ProductService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class ProductMenu implements IMenu{
    private final Customer customer;

    private final ProductService productService;
    private final CartService cartService;

    Scanner input = new Scanner(System.in);
    private Cart cart;

    private final boolean isLoggedIn;

    public ProductMenu(Customer customer, ProductService productService, CartService cartService, boolean loggedIn) {
        this.customer = customer;
        this.productService = productService;
        this.cartService = cartService;
        this.isLoggedIn = loggedIn;
    }

    @Override
    public void start() {
        exit:{
            while (true){
                if (isLoggedIn) {
                    System.out.println("\nWelcome " + customer.getfName() + "!");
                    cart = cartService.hasExistingCart(customer.getId());
                }
                else
                    System.out.println("Welcome to our Product!");

                System.out.println("\nShop by...");
                System.out.println("[S]picy");
                System.out.println("S[w]eet");
                System.out.println("[O]riginal");
                System.out.println("[C]art");
                System.out.println("[M]ain Menu");
                System.out.println("E[x]it Store");

                catExit:{
                    while (true){
                        System.out.print("\nEnter: ");
                        String category = input.nextLine().toUpperCase();

                        switch(category){
                            case "S":
                            case "W":
                            case "O":
                            case "M": new MainMenu(new CustomerService(new CustomerDAO())).start(customer, true); break exit;
                            case "X": break exit;
                            default:
                                System.out.println("\nInvalid entry. Try again"); break;
                        }
                    }
                }
            }
        }
    }

    private void printProduct(String category){
        List<Product> products = productService.getProduct(category);


    }
}
