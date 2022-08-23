package com.revature.exotic_jerky;

import com.revature.exotic_jerky.daos.CustomerDAO;
import com.revature.exotic_jerky.daos.OrderDAO;
import com.revature.exotic_jerky.daos.ProductDAO;
import com.revature.exotic_jerky.daos.StoreDAO;
import com.revature.exotic_jerky.models.Store;
import com.revature.exotic_jerky.services.CustomerService;
import com.revature.exotic_jerky.services.OrderService;
import com.revature.exotic_jerky.services.ProductService;
import com.revature.exotic_jerky.services.StoreService;
import com.revature.exotic_jerky.ui.InventoryMenu;
import com.revature.exotic_jerky.ui.MainMenu;

public class Main {
    public static void main(String[] args){
        new MainMenu(new CustomerService(new CustomerDAO()), new StoreService(new StoreDAO()), new OrderService(new OrderDAO())).start();
        System.out.println("\nThanks for Visiting! Hope to see you again soon!");
    }
}
