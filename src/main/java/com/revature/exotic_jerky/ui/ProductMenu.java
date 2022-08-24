package com.revature.exotic_jerky.ui;

import com.revature.exotic_jerky.daos.CustomerDAO;
import com.revature.exotic_jerky.daos.OrderDAO;
import com.revature.exotic_jerky.daos.StoreDAO;
import com.revature.exotic_jerky.models.Cart;
import com.revature.exotic_jerky.models.Customer;
import com.revature.exotic_jerky.models.Product;
import com.revature.exotic_jerky.models.Store;
import com.revature.exotic_jerky.services.*;
import com.revature.exotic_jerky.utils.custom_exceptions.InvalidCustomerException;

import java.util.*;

public class ProductMenu implements IMenu{
    private final Customer customer;
    private final Cart cart;
    private final Store store;
    private final CustomerService customerService;
    private final ProductService productService;
    private final CartService cartService;
    private final StoreService storeService;

    Scanner input = new Scanner(System.in);
    private boolean isLoggedIn;

    // Pre: None
    // Post: A new instance of ProductMenu is initialized
    // Purpose: To instantiate a new instance of ProductMenu
    public ProductMenu(Customer customer, CustomerService customerService, ProductService productService, CartService cartService, StoreService storeService, boolean loggedIn) {
        this.customer = customer;
        this.customerService = customerService;
        this.productService = productService;
        this.cartService = cartService;
        this.storeService = storeService;
        this.isLoggedIn = loggedIn;
        this.store = getStoreToShopFrom();
        this.cart = isLoggedInGetCart(loggedIn);
    }

    // Pre: Product is selected from the MainMenu
    // Post: A customer is brought to the product menu
    // Purpose: To start a product menu
    @Override
    public void start() {
        exit:{
            while (true){
                System.out.println("\nShop by...");
                System.out.println("[S]picy");
                System.out.println("S[w]eet");
                System.out.println("[O]riginal");
                System.out.println("\n[C]art");
                System.out.println("[M]ain Menu");
                System.out.println("E[x]it Store");

                categoryExit:{
                    while (true){
                        System.out.print("\nEnter: ");
                        String category = input.nextLine().toUpperCase();

                        if (category.equalsIgnoreCase("B")) break categoryExit;

                        Map<String, Product> productMap;

                        switch(category){
                            case "S": productMap = getProductListAndPrint("SPICY");
                                select(productMap, "SPICY"); break categoryExit;
                            case "W": productMap = getProductListAndPrint("SWEET");
                                select(productMap, "SWEET"); break categoryExit;
                            case "O": productMap = getProductListAndPrint("ORIGINAL");
                                select(productMap, "ORIGINAL"); break categoryExit;
                            case "C": new CartMenu(customer, cartService, new OrderService(new OrderDAO()), new StoreService(new StoreDAO()), productService, store, isLoggedIn).start();
                                break categoryExit;
                            case "M":
                                if (!isLoggedIn){
                                    new MainMenu(new CustomerService(new CustomerDAO()), new StoreService(new StoreDAO()), new OrderService(new OrderDAO())).start();
                                    break exit;
                                }
                                if (customer.getEmail() != null){
                                    new MainMenu(new CustomerService(new CustomerDAO()), new StoreService(new StoreDAO()), new OrderService(new OrderDAO())).start(customer, true);
                                    break exit;
                                }
                                new MainMenu(new CustomerService(new CustomerDAO()), new StoreService(new StoreDAO()), new OrderService(new OrderDAO())).start(customer, false);
                            case "X":
                                if (customer.getEmail() == null && cart.getTotal() != 0){
                                    if (!promptUserToSignUp())
                                        isLoggedIn = false;
                                }
                                break exit;
                            default:
                                System.out.println("\nInvalid entry. Try again"); break;
                        }
                    }
                }
            }
        }
        if (!isLoggedIn){
            if (cart.getTotal() != 0)
                cartService.deleteCartJCT(cart.getID());
            cartService.deleteCartByCustomerID(customer.getId());
            customerService.deleteCustomer(customer.getId());
        }
    }

    // Pre: A new ProductMenu is initialized
    // Post: The store for the customer to shop at is set
    // Purpose: To get the closest store to shop at
    private Store getStoreToShopFrom(){
        List<Store> stores = storeService.getAllStores();
        Store storeToShop = stores.get(0);
        int storeSelection = 99999;
        String zip;

        if (stores.size() > 1) {
            if (customer.getZip() == null) {
                exit:
                {
                    while (true) {
                        System.out.println("\nPlease enter your zip code to shop store");
                        System.out.println("\nFormat ##### or #####-####");
                        System.out.print("Enter: ");
                        zip = input.nextLine().toUpperCase();

                        try {
                            customerService.isValidZip(zip);
                            break exit;
                        } catch (InvalidCustomerException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
                customer.setZip(zip);
            }
            for (Store s : stores) {
                if (Math.abs(Integer.parseInt(s.getZip()) - Integer.parseInt(customer.getZip())) < storeSelection) {
                    storeToShop = s;
                    storeSelection = Integer.parseInt(s.getZip());
                }
            }
        }
        System.out.println("\nYou are now shopping the " + storeToShop.getCity() + " store.");
        return storeToShop;
    }

    // Pre: The user is trying to check out
    // Post: The user now has created an account
    // Purpose: To finish the sign-up of an account if they try to check out their cart
    private boolean promptUserToSignUp(){
        System.out.println("\nExiting the store now will delete your cart.");
        System.out.println("You can save it by making a profile.");

        System.out.println("\nSign Up? [Y]es/[N]o");
        System.out.print("Enter: ");

        while (true){
            switch(input.nextLine().toUpperCase()){
                case "Y": new SignUpMenu(customerService, new StoreService(new StoreDAO())).start(); return true;
                case "N": return false;
                default:
                    System.out.println("\nInvalid Entry! Try Again...");
            }
        }
    }

    // Pre: A customer is logged in
    // Post: A cart is returned if there is one on their account else return a new cart
    // Purpose: To check if the user has an existing cart
    private Cart isLoggedInGetCart(boolean isLoggedIn){
        if (isLoggedIn) {
            System.out.println("\nWelcome " + customer.getfName() + "!");
        }
        else{
            System.out.println("Welcome to our Product!");
            customerService.signUp(customer);
        }
        return cartService.hasExistingCart(customer.getId());
    }

    // Pre: ProductMenu must be started
    // Post: A list of products at a specific category is printed
    // Purpose: To print to the user all products from a specific category
    private Map<String, Product> getProductListAndPrint(String category){
        Map<String, Product> productMap = productService.getProduct(category);
        int index = 1, maxStrLength = 40;

        for (Product product : productMap.values()){
            // Output selection character and name
            System.out.print("\n[" + index + "] " + product.getName());

            // Get spacing distance
            maxStrLength -= product.getName().length() + String.valueOf(product.getPrice()).length() + 1;

            // Print until distance is reached
            for (int i = 0; i <= maxStrLength; i++)
                System.out.print("-");

            // Print price
            System.out.println("$" + product.getPrice());

            System.out.print("\tIn Stock Quantity");

            maxStrLength = 40 - "\t In Stock Quantity".length() - String.valueOf(product.getQuantity()).length();

            for (int i = 0; i <= maxStrLength; i++)
                System.out.print("-");

            System.out.println(product.getQuantity());

            // Print description
            System.out.println(product.getDescription());
            maxStrLength = 40;
            index++;
        }

        return productMap;
    }

    // Pre: A list of products is displayed to the user
    // Post: A user has selected a product from the list
    // Purpose: To allow the user to select a product
    private void select(Map<String, Product> productMap, String category){
        exit:{
            while (true){
                System.out.println("\nEnter [B]ack");
                System.out.print("Make a selection: ");
                String selection = input.nextLine();
                if (selection.equalsIgnoreCase("B")) break exit;

                try{
                    int index = Integer.parseInt(selection);

                    Product[] products = productMap.values().toArray(new Product[0]);

                    System.out.println("\nWould you like to add " + products[index - 1].getName() + " to your cart? [Y]es/[N]o");

                    selection = addToCart(products, index, category);

                    if (selection.equalsIgnoreCase("C")) break exit;

                } catch (NumberFormatException e){
                    System.out.println("\nInvalid Input. Must be a numeric value.");
                } catch (IndexOutOfBoundsException e){
                    System.out.println("\nInvalid Input. Must be a value between 1-" + productMap.size() + ".");
                }
            }
        }
    }

    // Pre: A user has selected a product
    // Post: Product is added to the users cart
    // Purpose: To add a product to the users cart
    private String addToCart(Product[] products, int index, String category){
        exitConfirmation:{
            while (true){
                System.out.print("Enter: ");
                // Get input
                switch (input.nextLine().toUpperCase()){
                    case "Y":
                        // Get quantity
                        while (true){
                            System.out.println("\n[C]ancel");
                            System.out.print("Quantity: ");
                            String str = input.nextLine();

                            // Check if they want to cancel
                            if (str.equalsIgnoreCase("C"))
                                return str;
                            // Verify they entered a numeric value
                            try{
                                byte quantity = (byte)Integer.parseInt(str);
                                // Insert into cart
                                if (quantity < products[index - 1].getQuantity()){
                                    float total = quantity * products[index - 1].getPrice();
                                    cart.setTotal(cart.getTotal() + total);

                                    if (!cartService.isExistingCart(customer.getId()))
                                        cartService.saveCart(cart, store.getId());

                                    cartService.updateCartTotal(cart);
                                    cart.setTotal(total);
                                    cartService.addToCart(cart, quantity, products[index - 1].getId());
                                    isLoggedIn = true;
                                    System.out.println("\nAdded to cart!");
                                    System.out.print("Enter to continue...");
                                    input.nextLine();
                                    getProductListAndPrint(category);
                                    break exitConfirmation;
                                }
                            } catch (NumberFormatException e){
                                System.out.println("\nInvalid Input. Must be a numeric value.");
                            }
                        }
                    case "N": getProductListAndPrint(category); break exitConfirmation;
                    default:
                        System.out.println("\nInvalid entry try again"); break;
                }
            }
        }
        return "";
    }
}