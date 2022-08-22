package com.revature.exotic_jerky.ui;

import com.revature.exotic_jerky.models.Product;
import com.revature.exotic_jerky.models.Store;
import com.revature.exotic_jerky.services.ProductService;
import com.revature.exotic_jerky.services.StoreService;

import java.util.Scanner;
import java.util.Set;

public class InventoryMenu implements IMenu{
    private final Store store;
    private final ProductService productService;

    private Scanner input = new Scanner(System.in);

    public InventoryMenu(Store store, ProductService productService) {
        this.store = store;
        this.productService = productService;
    }

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
                    case "G":
                    case "U": readFile(); break exit;
                    case "B": break exit;
                    default:
                        System.out.println("\nInvalid input. Try again...");
                }
            }
        }
    }

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
