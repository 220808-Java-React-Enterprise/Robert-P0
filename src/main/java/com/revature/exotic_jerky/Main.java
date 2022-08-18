package com.revature.exotic_jerky;

import com.revature.exotic_jerky.daos.CustomerDAO;
import com.revature.exotic_jerky.services.CustomerService;
import com.revature.exotic_jerky.ui.MainMenu;

public class Main {
    public static void main(String[] args){
        new MainMenu(new CustomerService(new CustomerDAO())).start();
    }
}
