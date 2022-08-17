package ui;

import models.Customer;
import services.CustomerService;
import utils.Exceptions.InvalidCustomerException;

import java.sql.SQLOutput;
import java.util.Scanner;
import java.util.UUID;

public class SignUpMenu implements IMenu{
    private final CustomerService customer;
    Scanner input = new Scanner(System.in);
    public SignUpMenu(){
        this.customer = new CustomerService();
    }
    @Override
    public void start() {
        System.out.println("Sign Up!");
        String fName, lName, email, address, city, state, zip, phone;
        String pass;

        fName = fName(); lName = lName();
        email = email(); pass = pass();
        address = address(); city = city();
        state = state(); zip = zip();
        phone = phone();

        confirm:{
            while (true){
                System.out.println("\nSummary:");
                System.out.println("Name: " + fName + " " + lName);
                System.out.println("Email: " + email);
                System.out.println("Address: " + address + ", " + city + ", " + state + " " + zip);
                System.out.println("Phone: " + phone);
                System.out.println("\nPlease confirm account Sign up " + fName + "!");
                System.out.println("[Y]es");
                System.out.println("[N]o");
                System.out.println("[U]pdate");
                System.out.print("\nEnter: ");

                switch(input.nextLine().toUpperCase()){
                    case "Y":
                        Customer cust = new Customer(UUID.randomUUID().toString(),
                                fName, lName, email, pass, address, city, state, zip, phone);
                        customer.signUp(cust);
                    case "N":
                        break confirm;
                    case "U":
                        updateExit:{
                            while (true){
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
                                System.out.println("[B]ack");
                                System.out.print("\nEnter: ");

                                switch (input.nextLine().toUpperCase()){
                                    case "F": fName = fName();
                                        System.out.println("\nFirst name updated"); break;
                                    case "L": lName = lName();
                                        System.out.println("\nLast name updated"); break;
                                    case "E": email = email();
                                        System.out.println("\nE-mail updated"); break;
                                    case "P": pass = pass();
                                        System.out.println("\nPassword updated"); break;
                                    case "A": address = address();
                                        System.out.println("\nAddress updated"); break;
                                    case "C": city = city();
                                        System.out.println("\nCity updated"); break;
                                    case "S": state = state();
                                        System.out.println("\nState updated"); break;
                                    case "Z": zip = zip();
                                        System.out.println("\nZip code updated"); break;
                                    case "H": phone = phone();
                                        System.out.println("\nPhone number updated"); break;
                                    case "B": break updateExit;
                                    default: System.out.println("\nInvalid entry! Try Again...");
                                }

                                System.out.println("\nUpdate More? [Y]es/[N]o");
                                System.out.print("Enter: ");

                                switch(input.nextLine().toUpperCase()){
                                    case "Y": break;
                                    case "N": break updateExit;
                                    default: System.out.println("\nInvalid entry! Try Again...");
                                }
                            }
                        }
                    default:
                        System.out.println("\nInvalid entry! Try Again...");
                        break;
                }
            }
        }
    }

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
