package com.revature.exotic_jerky.ui;

import com.revature.exotic_jerky.daos.*;
import com.revature.exotic_jerky.models.*;
import com.revature.exotic_jerky.services.*;

import java.util.*;

public class CartMenu implements IMenu{
    private final CartService cartService;
    private final OrderService orderService;
    private final StoreService storeService;
    private final ProductService productService;
    private final Cart cart;
    private final Store store;
    private Customer customer;
    private boolean isLoggedIn;

    Scanner input = new Scanner(System.in);

    // Non-Default Constructor
    public CartMenu(Customer customer, CartService cartService, OrderService orderService, StoreService storeService, ProductService productService, Store store, boolean isLoggedIn) {
        this.customer = customer;
        this.cartService = cartService;
        this.orderService = orderService;
        this.storeService = storeService;
        this.productService = productService;
        this.store = store;
        this.cart = cartService.hasExistingCart(customer.getId());
        this.isLoggedIn = isLoggedIn;
    }

    // Pre: A instance of the CartMenu is started
    // Post: A user has left the cartMenu
    // Purpose: To give the user a cart for purchasing products
    @Override
    public void start() {
        Map<String, List<String>> summary;
        exit:{
            while (true){
                System.out.print("\nWelcome to your cart");

                if (isLoggedIn) System.out.println(" " + customer.getfName() + "!");
                else System.out.println("!");

                System.out.println("\nSummary:");
                summary = getCart(cart.getID());

                if (summary.size() == 0){
                    System.out.println("\nCart is empty! :(");
                    System.out.println("\nWould you like to check our products? [Y]es/[N]o");
                    System.out.print("Enter: ");

                    while (true){
                        switch(input.nextLine().toUpperCase()){
                            case "Y": new ProductMenu(new Customer(), new CustomerService(new CustomerDAO()), new ProductService(new ProductDAO()), new CartService(new CartDAO()), storeService, isLoggedIn).start(); break exit;
                            case "N": break exit;
                            default:
                                System.out.println("\nInvalid Entry! Try Again...");
                        }
                    }
                }

                System.out.println("\nWhat would you like to do?");
                System.out.println("\n[U]pdate Cart");
                System.out.println("[C]heck Out");
                System.out.println("[B]ack");

                exitChoice:{
                    while (true){
                        System.out.print("\nEnter: ");

                        switch(input.nextLine().toUpperCase()){
                            case "U": updateCart(summary); break exitChoice;
                            case "C": checkOut(summary); break exit;
                            case "B": break exit;
                            default:
                                System.out.println("\nInvalid input. Try Again...");
                                break;
                        }
                    }
                }

            }
        }
    }

    // Pre: To get the users cart from the database
    // Post: The users cart is displayed to the user
    // Purpose: To get the users cart from the database
    private Map<String, List<String>> getCart(String customerID) {
        Map<String, List<String>> map = cartService.getCheckOutCart(cart.getCustomerID());
        int index = 1, maxStrLength = 30;
        float grandTotal = 0.00f;

        for (List<String> details : map.values()) {
            grandTotal = printProduct(details, grandTotal);
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

    // Pre: A user has started the cart menu
    // Post: The user has purchased their cart
    // Purpose: To allow the user to purchase the products in their cart
    private void checkOut(Map<String, List<String>> summary){
        if (customer.getEmail() == null){
            System.out.println("\nYou must login to continue with checkout.");

            System.out.println("\n[L]ogin");
            System.out.println("[S]ign Up");
            System.out.println("[C]ancel");

            exit:{
                while (true){
                    System.out.print("Enter: ");
                    switch(input.nextLine().toUpperCase()){
                        case "L": customer = new LoginMenu(new CustomerService(new CustomerDAO()), cartService, new StoreService(new StoreDAO())).start(customer, cart);
                            if (customer.getAddress() == null){
                                System.out.println("\nYou must finish account sign up to place order");
                                customer = new SignUpMenu(new CustomerService(new CustomerDAO()), new StoreService(new StoreDAO())).start(customer, false);
                            }
                            break exit;
                        case "S": customer = new SignUpMenu(new CustomerService(new CustomerDAO()), new StoreService(new StoreDAO())).start(customer, true);
                            break exit;
                        case "C": break exit;
                        default:
                            System.out.println("\nInvalid Entry! Try Again...");
                    }
                }
            }
        }

        getCart(customer.getId());
        confirmOrder(summary);
    }

    // Pre:
    // Post:
    // Purpose:
    private void confirmOrder(Map<String, List<String>> summary){
        System.out.println("\nConfirm Order. [Y]es/[N]o");

        exit:{
            while (true){
                System.out.print("Enter: ");
                switch(input.nextLine().toUpperCase()){
                    case "Y":
                        for (List<String> p : summary.values()){
                            Product product = productService.getProductByID(p.get(4));
                        }

                        // Save Order and Delete Cart
                        String orderID = UUID.randomUUID().toString();
                        orderService.saveOrder(new Orders(orderID, String.valueOf(new Date()), customer.getId(), store.getId(), cart.getTotal()), store.getId());
                        for (List<String> product : summary.values()){
                            orderService.saveJCT(Byte.parseByte(product.get(2)), Float.parseFloat(product.get(3)), orderID, product.get(4));
                            // Update product inventory
                            productService.updateProductQuantity(Byte.parseByte(product.get(2)), product.get(4));
                        }
                        // Delete Cart
                        cartService.deleteCartJCT(cart.getID());
                        cartService.deleteCartByCustomerID(customer.getId());

                        System.out.println("\nThanks for ordering! ;)");
                        new MainMenu(new CustomerService(new CustomerDAO()), new StoreService(new StoreDAO()), new OrderService(new OrderDAO())).start(customer, isLoggedIn);
                    case "N": break exit;
                    default:
                        System.out.println("\nInvalid Entry! Try Again...");
                }
            }
        }
    }

    // Pre: To allow the user to update their cart
    // Post: The user has updated their cart
    // Purpose: To allow the user to update their cart
    private void updateCart(Map<String, List<String>> summary){
        System.out.println("Select the product you would like to update");
        exit:{
            while (true){
                System.out.println("\nEnter [B]ack");
                System.out.print("Make a selection: ");
                String selection = input.nextLine();
                if (selection.equalsIgnoreCase("B")) break exit;

                try{
                    int index = Integer.parseInt(selection);

                    if (index > summary.size()){
                        System.out.println("\nInvalid Entry! Try Again...");
                        break;
                    }

                    int i = 1;
                    List<String> product = null;
                    for (List<String> products : summary.values()){
                        if (i == index) { product = products; break; }
                    }

                    if (product != null){
                        printProduct(product, 0);

                        System.out.println("\nWhat would you like to do?");
                        System.out.println("\n[U]pdate quantity");
                        System.out.println("[D]elete from cart");
                        System.out.println("[B]ack");


                        exitUpdate:{
                            while (true){
                                System.out.print("\nEnter: ");

                                switch (input.nextLine().toUpperCase()){
                                    case "U": updateQuantity(product.get(4));
                                    case "D": deleteFromCart(product.get(4)); break exitUpdate;
                                    case "B": break exitUpdate;
                                    default:
                                        System.out.println("\nInvalid Entry! Try Again...");
                                }
                            }
                        }
                    }

                } catch (NumberFormatException e){
                    System.out.println("\nInvalid Input. Must be a numeric value.");
                } catch (IndexOutOfBoundsException e){
                    System.out.println("\nInvalid Input. Must be a value between 1-" + summary.size() + ".");
                }
            }
        }
    }

    // Pre: The user has called to update cart
    // Post: A product is removed from the users cart
    // Purpose: To remove a product from the users cart
    private void deleteFromCart(String productID){
        //needs implementation
    }

    // Pre: The user wants to update their cart
    // Post: The quantity of a product in a users cart is updated
    // Purpose: To update the quantity of a product in a users cart
    private void updateQuantity(String productID){

    }

    // Pre: A list of the carts products is passed in
    // Post: The product is printed to the user
    // Purpose: To print the product to the user
    private float printProduct(List<String> product, float grandTotal){
        int index = 1, maxStrLength = 30;
        // Output selection character and name
        System.out.print("\n[" + index + "] " + product.get(0));

        maxStrLength -= product.get(0).length() + product.get(1).length() + 1;

        for (int i = 0; i < maxStrLength; i++)
            System.out.print("-");

        System.out.println("$" + product.get(1));

        System.out.print("\tQuantity");

        maxStrLength = 30 - "\tQuantity".length() - product.get(2).length() - 2;

        for (int i = 0; i < maxStrLength; i++)
            System.out.print("-");

        System.out.println(product.get(2));

        System.out.print("\tTotal");

        maxStrLength = 30 - "\tTotal".length() - product.get(3).length() + 1;

        for (int i = 0; i < maxStrLength; i++)
            System.out.print("-");

        System.out.println("$" + String.format("%.2f", Float.parseFloat(product.get(3))));

        return grandTotal + Float.parseFloat(product.get(3));
    }
}