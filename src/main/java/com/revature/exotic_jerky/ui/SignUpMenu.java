package com.revature.exotic_jerky.ui;

import com.revature.exotic_jerky.models.Customer;
import com.revature.exotic_jerky.services.CustomerService;
import com.revature.exotic_jerky.utils.custom_exceptions.InvalidCustomerException;

import java.util.Scanner;
import java.util.UUID;

public class SignUpMenu implements IMenu{
    private final CustomerService customer;

    // Pre:
    // Post:
    // Purpose:
    Scanner input = new Scanner(System.in);

    // Pre:
    // Post:
    // Purpose:
    public SignUpMenu(CustomerService customer) {
        this.customer = customer;
    }

    // Pre: A new instance of SignUpMenu is called
    // Post: A customer has signed up or canceled sign up
    // Purpose: To sign up a customer to the database
    @Override
    public void start() {
        System.out.println("Sign Up!");

        Customer cust = new Customer(UUID.randomUUID().toString(),
                fName(), lName(), email(), pass(), address(), city(), state(), zip(), phone());

        confirm:{
            while (true){
                printSummaryOfCustomer(cust);

                System.out.println("\nPlease confirm account Sign up " + cust.getfName() + "!");
                System.out.println("[Y]es");
                System.out.println("[N]o");
                System.out.println("[U]pdate");
                System.out.print("\nEnter: ");

                switch(input.nextLine().toUpperCase()){
                    case "Y":
                        customer.signUp(cust);
                        break confirm;
                    case "N":
                        new MainMenu().start();
                        break confirm;
                    case "U":
                        cust = updateInfo(cust);
                    default:
                        System.out.println("\nInvalid entry! Try Again...");
                        break;
                }
            }
        }
    }

    // Pre: A instance of Customer must be instantiated
    // Post: A summary of the Customers credentials is printed
    // Purpose: To print the Customers information
    private static void printSummaryOfCustomer(Customer cust){
        System.out.println("\nSummary:");
        System.out.println("Name: " + cust.getfName() + " " + cust.getlName());
        System.out.println("Email: " + cust.getEmail());
        System.out.println("Address: " + cust.getAddress() + ", " + cust.getCity()
                + ", " + cust.getState() + " " + cust.getZip());
        System.out.println("Phone: " + cust.getPhone());
    }

    // Pre: A Customer has been instantiated, and they request to update
    // Post: A Customer is returned with updated credentials
    // Purpose: To update a Customers credentials
    private Customer updateInfo(Customer cust){
        Customer updated = cust;
        exit:{
            while (true){
                updateExit:{
                    System.out.println("\nWhat would you like to update!");
                    System.out.println("[F]irst Name");
                    System.out.println("[L]ast Name");
                    System.out.println("[E]mail");
                    System.out.println("[P]assword");
                    System.out.println("[A]ddress");
                    System.out.println("[C]ity");
                    System.out.println("[S]tate");
                    System.out.println("[Z]ip");
                    System.out.println("P[h]one");
                    System.out.println("[B]ack/Cancel");

                    while (true){
                        System.out.print("\nEnter: ");
                        switch (input.nextLine().toUpperCase()){
                            case "F": updated.setfName(fName());
                                System.out.println("\nFirst name updated"); break updateExit;
                            case "L": updated.setlName(lName());
                                System.out.println("\nLast name updated"); break updateExit;
                            case "E": updated.setEmail(email());
                                System.out.println("\nE-mail updated"); break updateExit;
                            case "P": updated.setPassword(pass());
                                System.out.println("\nPassword updated"); break updateExit;
                            case "A": updated.setAddress(address());
                                System.out.println("\nAddress updated"); break updateExit;
                            case "C": updated.setCity(city());
                                System.out.println("\nCity updated"); break updateExit;
                            case "S": updated.setState(state());
                                System.out.println("\nState updated"); break updateExit;
                            case "Z": updated.setZip(zip());
                                System.out.println("\nZip code updated"); break updateExit;
                            case "H": updated.setPhone(phone());
                                System.out.println("\nPhone number updated"); break updateExit;
                            case "B": return cust;
                            default: System.out.println("\nInvalid entry! Try Again...");
                        }
                    }
                }
                verifyExit:{
                    while (true){
                        printSummaryOfCustomer(updated);
                        System.out.println("\nUpdate More? [Y]es/[N]o/[C]ancel");
                        System.out.print("Enter: ");

                        switch(input.nextLine().toUpperCase()){
                            case "Y": break verifyExit;
                            case "N": break exit;
                            case "C": return cust;
                            default: System.out.println("\nInvalid entry! Try Again...");
                        }
                    }
                }
            }
        }
        return updated;
    }

    // Pre: Non
    // Post: A String value is returned
    // Purpose: To get users input for their first name
    private String fName(){
        String fName;
        fNameExit:{
            while (true){
                System.out.print("\nFirst Name: ");
                fName = input.nextLine();
                fName = fName.substring(0,1).toUpperCase() + fName.substring(1).toLowerCase();

                try{
                    customer.isValidName(fName);
                    break fNameExit;
                }catch(InvalidCustomerException e){
                    System.out.println(e.getMessage());
                }
            }
        }
        return fName;
    }

    // Pre: Non
    // Post: A String value is returned
    // Purpose: To get users input for their last name
    private String lName(){
        String lName;
        lNameExit:{
            while (true) {
                System.out.print("\nLast Name: ");
                lName = input.nextLine();
                lName = lName.substring(0,1).toUpperCase() + lName.substring(1).toLowerCase();

                try{
                    customer.isValidName(lName);
                    break lNameExit;
                }catch(InvalidCustomerException e){
                    System.out.println(e.getMessage());
                }
            }
        }
        return lName;
    }

    // Pre: Non
    // Post: A String value is returned
    // Purpose: To get users input for their email
    private String email(){
        String email;
        emailExit:{
            while (true){
                System.out.print("\nE-mail: ");
                email = input.nextLine();

                try{
                    customer.isValidEmail(email);
                    break emailExit;
                }catch(InvalidCustomerException e){
                    System.out.println(e.getMessage());
                }
            }
        }
        return email;
    }

    // Pre: Non
    // Post: A String value is returned
    // Purpose: To get users input for their password
    private String pass(){
        String pass;
        passExit:{
            while (true){
                System.out.print("\nEnter a password: ");
                pass = input.nextLine();

                try{
                    customer.isValidPassword(pass);
                    String tempPass = pass;
                    passConfirm:{
                        while (true){
                            System.out.print("\nConfirm password: ");
                            pass = input.nextLine();

                            if (pass.equalsIgnoreCase("B"))
                                break passConfirm;
                            else if (pass.equals(tempPass))
                                break passExit;
                            System.out.println("\nPasswords Don't Match! Try again... Or [B]ack to re-enter password.");
                        }
                    }
                } catch(InvalidCustomerException e){
                    System.out.println(e.getMessage());
                }
            }
        }
        return pass;
    }

    // Pre: Non
    // Post: A String value is returned
    // Purpose: To get users input for their address
    private String address(){
        String address;
        addressExit:{
            while (true){
                System.out.println("\nAddr# Street Name");
                System.out.print("Street Address: ");
                address = input.nextLine();

                try{
                    customer.isValidAddress(address);
                    address = address.toUpperCase();
                    break addressExit;
                }catch (InvalidCustomerException e){
                    System.out.println(e.getMessage());
                }
            }
        }
        return address;
    }

    // Pre: Non
    // Post: A String value is returned
    // Purpose: To get users input for their city
    private String city(){
        String city;
        cityExit:{
            while (true){
                System.out.print("\nCity: ");
                city = input.nextLine();
                city = city.substring(0,1).toUpperCase() + city.substring(1);

                try{
                    customer.isValidName(city);
                    break cityExit;
                } catch (InvalidCustomerException e){
                    System.out.println(e.getMessage());
                }
            }
        }
        return city;
    }

    // Pre: Non
    // Post: A String value is returned
    // Purpose: To get users input for their state
    private String state(){
        String state;
        stateExit:{
            while (true){
                System.out.println("\nState abbreviation");
                System.out.print("State: ");
                state = input.nextLine().toUpperCase();

                try{
                    customer.isValidState(state);
                    break stateExit;
                } catch (InvalidCustomerException e){
                    System.out.println(e.getMessage());
                }
            }
        }
        return state;
    }

    // Pre: Non
    // Post: A String value is returned
    // Purpose: To get users input for their zip code
    private String zip(){
        String zip;
        zipExit:{
            while (true){
                System.out.println("\nFormat ##### or #####-####");
                System.out.print("Zip: ");
                zip = input.nextLine();

                try{
                    customer.isValidZip(zip);
                    break zipExit;
                } catch (InvalidCustomerException e){
                    System.out.println(e.getMessage());
                }
            }
        }
        return zip;
    }

    // Pre: Non
    // Post: A String value is returned
    // Purpose: To get users input for their phone number
    private String phone(){
        String phone;
        phoneExit:{
            while (true){
                System.out.println("\nFormat ###-###-####");
                System.out.print("Phone: ");
                phone = input.nextLine();

                try{
                    customer.isValidPhone(phone);
                    break phoneExit;
                } catch (InvalidCustomerException e){
                    System.out.println(e.getMessage());
                }
            }
        }
        return phone;
    }
}