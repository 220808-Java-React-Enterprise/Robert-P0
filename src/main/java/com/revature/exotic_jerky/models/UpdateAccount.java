package com.revature.exotic_jerky.models;

import com.revature.exotic_jerky.daos.CustomerDAO;
import com.revature.exotic_jerky.models.Customer;
import com.revature.exotic_jerky.services.CustomerService;
import com.revature.exotic_jerky.ui.MainMenu;
import com.revature.exotic_jerky.utils.custom_exceptions.InvalidCustomerException;

import java.util.Scanner;

public class UpdateAccount {
    private final CustomerService customerService;

    Scanner input = new Scanner(System.in);

    // Pre: None
    // Post: A instance of UpdateAccount is instantiated
    // Purpose: Non-Default Constructor
    protected UpdateAccount(CustomerService customerService) {
        this.customerService = customerService;
    }

    // Pre: A instance of Customer must be instantiated
    // Post: A summary of the Customers credentials is printed
    // Purpose: To print the Customers information
    public static void printSummaryOfCustomer(Customer customer){
        System.out.println("\nSummary:");
        System.out.println("Name: " + customer.getfName() + " " + customer.getlName());
        System.out.println("Email: " + customer.getEmail());
        System.out.println("Address: " + customer.getAddress() + ", " + customer.getCity()
                + ", " + customer.getState() + " " + customer.getZip());
        System.out.println("Phone: " + customer.getPhone());
    }

    // Pre: A Customer has been instantiated, and they request to update
    // Post: A Customer is returned with updated credentials
    // Purpose: To update a Customers credentials
    protected Customer updateInfo(Customer customer){
        Customer updated = new Customer(customer);

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
                        case "P": updated.setPassword(pass(customer.getPassword()));
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
                        case "M": new MainMenu(new CustomerService(new CustomerDAO())).start(customer, true); break updateExit;
                        case "B": return customer;
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
                        case "N": customerService.updateAccount(updated); return updated;
                        case "C": return customer;
                        default: System.out.println("\nInvalid entry! Try Again...");
                    }
                }
            }
        }
    }

    // Pre: Non
    // Post: A String value is returned
    // Purpose: To get users input for their first name
    protected String fName(){
        String fName;
        fNameExit:{
            while (true){
                System.out.print("\nFirst Name: ");
                fName = input.nextLine();

                if (fName.equalsIgnoreCase("M"))
                    return "M";

                fName = fName.substring(0,1).toUpperCase() + fName.substring(1).toLowerCase();

                try{
                    customerService.isValidName(fName);
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
    protected String lName(){
        String lName;
        lNameExit:{
            while (true) {
                System.out.print("\nLast Name: ");
                lName = input.nextLine();

                if (lName.equalsIgnoreCase("M"))
                    return "M";

                lName = lName.substring(0,1).toUpperCase() + lName.substring(1).toLowerCase();

                try{
                    customerService.isValidName(lName);
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
    protected String email(){
        String email;
        emailExit:{
            while (true){
                System.out.print("\nE-mail: ");
                email = input.nextLine();

                if (email.equalsIgnoreCase("M"))
                    return "M";

                try{
                    if (customerService.isDuplicateEmail(email))
                        System.out.println(email + " already exists.");
                    customerService.isValidEmail(email);
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
    protected String pass(){
        String pass;
        passExit:{
            while (true){
                System.out.print("\nEnter a password: ");
                pass = input.nextLine();

                if (pass.equalsIgnoreCase("M"))
                    return "M";

                try{
                    customerService.isValidPassword(pass);
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

    // Pre: None
    // Post: Password is updated
    // Purpose: To update the password
    protected String pass(String pass){
        String oldPass;
        String newPass;
        exit:{
            while (true){
                System.out.println("\nEnter old password. Or [B]ack ");
                System.out.print("Enter: ");
                oldPass = input.nextLine();
                if (oldPass.equalsIgnoreCase("B"))
                    return pass;
                else if (!oldPass.equals(pass))
                    System.out.println("\nIncorrect password.");
                else
                    break exit;
            }
        }

        passExit:{
            while (true){
                System.out.print("\nEnter a new password: ");
                newPass = input.nextLine();

                try{
                    customerService.isValidPassword(newPass);
                    String tempPass = newPass;
                    passConfirm:{
                        while (true){
                            System.out.print("\nConfirm password: ");
                            newPass = input.nextLine();

                            if (newPass.equalsIgnoreCase("B"))
                                break passConfirm;
                            else if (newPass.equals(tempPass))
                                break passExit;
                            System.out.println("\nPasswords Don't Match! Try again... Or [B]ack to re-enter password.");
                        }
                    }
                } catch(InvalidCustomerException e){
                    System.out.println(e.getMessage());
                }
            }
        }

        return newPass;
    }

    // Pre: Non
    // Post: A String value is returned
    // Purpose: To get users input for their address
    protected String address(){
        String address;
        addressExit:{
            while (true){
                System.out.println("\nAddr# Street Name");
                System.out.print("Street Address: ");
                address = input.nextLine();

                if (address.equalsIgnoreCase("M"))
                    return "M";

                try{
                    customerService.isValidAddress(address);
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
    protected String city(){
        String city;
        cityExit:{
            while (true){
                System.out.print("\nCity: ");
                city = input.nextLine();

                if (city.equalsIgnoreCase("M"))
                    return "M";

                city = city.substring(0,1).toUpperCase() + city.substring(1);

                try{
                    customerService.isValidName(city);
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
    protected String state(){
        String state;
        stateExit:{
            while (true){
                System.out.println("\nState abbreviation");
                System.out.print("State: ");
                state = input.nextLine().toUpperCase();

                if (state.equalsIgnoreCase("M"))
                    return "M";

                try{
                    customerService.isValidState(state);
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
    protected String zip(){
        String zip;
        zipExit:{
            while (true){
                System.out.println("\nFormat ##### or #####-####");
                System.out.print("Zip: ");
                zip = input.nextLine();

                if (zip.equalsIgnoreCase("M"))
                    return "M";

                try{
                    customerService.isValidZip(zip);
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
    protected String phone(){
        String phone;
        phoneExit:{
            while (true){
                System.out.println("\nFormat ###-###-####");
                System.out.print("Phone: ");
                phone = input.nextLine();

                if (phone.equalsIgnoreCase("M"))
                    return "M";

                try{
                    customerService.isValidPhone(phone);
                    break phoneExit;
                } catch (InvalidCustomerException e){
                    System.out.println(e.getMessage());
                }
            }
        }
        return phone;
    }
}
