package com.revature.exotic_jerky.ui;

import com.revature.exotic_jerky.models.Store;
import com.revature.exotic_jerky.services.ProductService;

import java.util.Scanner;

public class InventoryMenu implements IMenu{
    private final Store store;
    private final ProductService productService;

    private Scanner input = new Scanner(System.in);

    // Pre: The admin has called to the Inventory Menu
    // Post: The inventory menu is displayed to the admin
    // Purpose: To allow the admin functionality of the inventory
    public InventoryMenu(Store store, ProductService productService) {
        this.store = store;
        this.productService = productService;
    }

    // Pre: Admin called for the inventory Menu
    // Post: The inventory menu is displayed to the admin
    // Purpose: To give the admin the ability to update the store inventory
    @Override
    public void start() {
        System.out.println("\nInventory");
        System.out.println("What would you like to do?");
        System.out.println("\n[G]et current Inventory");
        System.out.println("[U]pdate current Inventory");
        System.out.println("[B]ack");

        exit:{
            while (true){
                System.out.print("\nEnter: ");

                switch(input.nextLine().toUpperCase()){
                    case "G": getInventory(); break exit;
                    case "U": readFile(); break exit;
                    case "B": break exit;
                    default:
                        System.out.println("\nInvalid input. Try again...");
                }
            }
        }
    }

    // Pre:
    // Post:
    // Purpose:
    private void getInventory(){
        String file;

        System.out.println("\nFile must be in .xlsx(exclude .xlsx)");
        System.out.print("Enter file name: ");
        file = input.nextLine();

        productService.readInventory(file);
        System.out.println("\nInventory successfully saved to " + file);
    }

    // Pre: A admin is logged in and looking to update the inventory
    // Post: The inventory of the store is updated
    // Purpose: To read the .xlsx file from the user and update the database inventory
    private void readFile(){
        String file;

        exit:{
            while (true){
                System.out.println("\nFile must be in .xlsx(exclude .xlsx)");
                System.out.print("Enter file name: ");

                if (productService.writeInventory(input.nextLine(), store.getId())){
                    System.out.println("\nInventory updated successfully"); break exit;
                }
            }
        }
    }
}
