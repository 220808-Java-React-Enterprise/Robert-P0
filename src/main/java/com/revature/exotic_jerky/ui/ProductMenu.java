package com.revature.exotic_jerky.ui;

import com.revature.exotic_jerky.daos.CustomerDAO;
import com.revature.exotic_jerky.models.Cart;
import com.revature.exotic_jerky.models.Customer;
import com.revature.exotic_jerky.models.Product;
import com.revature.exotic_jerky.services.CartService;
import com.revature.exotic_jerky.services.CustomerService;
import com.revature.exotic_jerky.services.ProductService;

import java.util.*;

public class ProductMenu implements IMenu{
    private final Customer customer;
    private final Cart cart;
    private final CustomerService customerService;
    private final ProductService productService;
    private final CartService cartService;

    Scanner input = new Scanner(System.in);
    private boolean isLoggedIn;

    // Pre: None
    // Post: A new instance of ProductMenu is initialized
    // Purpose: To instantiate a new instance of ProductMenu
    public ProductMenu(Customer customer, CustomerService customerService, ProductService productService, CartService cartService, boolean loggedIn) {
        this.customer = customer;
        this.customerService = customerService;
        this.productService = productService;
        this.cartService = cartService;
        this.isLoggedIn = loggedIn;
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
                System.out.println("[C]art");
                System.out.println("[M]ain Menu");
                System.out.println("E[x]it Store");

                categoryExit:{
                    while (true){
                        System.out.print("Enter: ");
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
                            case "C": new CartMenu(customer, cart, cartService).start();
                            case "M":
                                if (isLoggedIn)
                                    new MainMenu(new CustomerService(new CustomerDAO())).start(customer, true);
                                new MainMenu(new CustomerService(new CustomerDAO())).start();
                                break exit;
                            case "X": break exit;
                            default:
                                System.out.println("\nInvalid entry. Try again"); break;
                        }
                    }
                }
            }
        }
        if (!isLoggedIn){
            cartService.deleteCartByCustomerID(customer.getId());
            customerService.deleteCustomer(customer.getId());
        }
    }

    // Pre: Pre
    // Post:
    // Purpose:
    private Cart isLoggedInGetCart(boolean isLoggedIn){
        if (isLoggedIn) {
            System.out.println("\nWelcome " + customer.getfName() + "!");
            return cartService.hasExistingCart(customer.getId());
        }
        else{
            System.out.println("Welcome to our Product!");
            customerService.signUp(customer);
            Cart newCart = new Cart(UUID.randomUUID().toString(), 0.00f, new Date(), customer.getId() );
            cartService.saveCart(newCart);
            return newCart;
        }
    }

    // Pre: ProductMenu must be started
    // Post: A list of products at a specific category is printed
    // Purpose: To print to the user all products from a specific category
    private Map<String, Product> getProductListAndPrint(String category){
        Map<String, Product> productMap = productService.getProduct(category);
        int index = 1, maxStrLengh = 30;

        for (Product product : productMap.values()){
            // Output selection character and name
            System.out.print("\n[" + index + "] " + product.getName());

            // Get spacing distance
            maxStrLengh -= product.getName().length() - String.valueOf(product.getPrice()).length();

            // Print until distance is reached
            for (int i = 0; i <= maxStrLengh; i++)
                System.out.print("-");

            // Print price
            System.out.println(product.getPrice());

            // Print description
            System.out.println(product.getDescription());
            maxStrLengh = 30;
            index++;
        }

        return productMap;
    }

    // Pre: A list of products is displayed to the user
    // Post:
    // Purpose:
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

    // Pre:
    // Post:
    // Purpose:
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
                                    cartService.updateCartTotal(cart);
                                    cartService.addToCart(cart, quantity, total, products[index - 1]);
                                    isLoggedIn = true;
                                    System.out.println("\nAdded to cart!");
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