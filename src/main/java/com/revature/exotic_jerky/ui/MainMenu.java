package com.revature.exotic_jerky.ui;

import com.revature.exotic_jerky.daos.CustomerDAO;
import com.revature.exotic_jerky.services.CustomerService;

import java.util.Scanner;

public class MainMenu implements IMenu{
    @Override
    public void start() {
        String input;
        Scanner scan = new Scanner(System.in);
        System.out.println("\nWelcome to Exotic Jerky!");

        exit:
        {
            while (true){
                System.out.println("[P]roduct");
                System.out.println("[L]ogin");
                System.out.println("[S]ign Up");
                System.out.println("E[x]it");

                System.out.print("\nEnter: ");
                input = scan.nextLine().toUpperCase();

                if (input.equals("P")){

                }
                else if (input.equals("L")){
                    new LoginMenu().start();
                }
                else if (input.equals("S")){
                    new SignUpMenu(new CustomerService(new CustomerDAO())).start();
                }
                else if (input.equals("X")){
                    System.out.println("Thanks for Visiting!"); break exit;
                }
            }
        }
    }
}
