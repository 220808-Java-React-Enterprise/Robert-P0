package com.revature.exotic_jerky.services;

import com.revature.exotic_jerky.daos.CustomerDAO;
import com.revature.exotic_jerky.models.Customer;
import com.revature.exotic_jerky.utils.InvalidCustomerException;

public class CustomerService {
    private final CustomerDAO custDAO;

    public CustomerService(){
        custDAO = new CustomerDAO();
    }

    public CustomerService(CustomerDAO custDAO){
        this.custDAO = custDAO;
    }

    public void signUp(Customer cust){
        custDAO.save(cust);
    }

    public void isValidName(String name){
        if (!name.matches("^[\\p{L} .'-]+$")) throw new InvalidCustomerException("\nInvalid format!");
    }

    public void isValidEmail(String email){
        if (!email.matches("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$"))
            throw new InvalidCustomerException("\nInvalid email format!");
    }

    public void isValidAddress(String address){
        if (!address.matches("^(\\d{1,}) [a-zA-Z0-9\\s]+(\\,)? [a-zA-Z]+(\\,)?$"))
            throw new InvalidCustomerException("Invalid address! Try again...");
    }

    public void isValidState(String state){
        if (!state.matches("^(?-i:A[LKSZRAEP]|C[AOT]|D[EC]|F[LM]|G[AU]|HI|I[ADLN]|K[SY]|LA|M[ADEHINOPST]|N[CDEHJMVY]|O[HKR]|P[ARW]|RI|S[CD]|T[NX]|UT|V[AIT]|W[AIVY])$"))
            throw new InvalidCustomerException("\nInvalid state abbreviation");
    }

    public void isValidZip(String zip){
        if (!zip.matches("^[0-9]{5}(?:-[0-9]{4})?$"))
            throw new InvalidCustomerException("\nInvalid zip code format");
    }

    public void isValidPhone(String phone){
        if (!phone.matches("^(\\d{3}[- .]?){2}\\d{4}$"))
            throw new InvalidCustomerException("\nInvalid phone number format");
    }

    public void isValidPassword(String pass){
        if (!pass.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$"))
            throw new InvalidCustomerException("\nInvalid password! Minimum eight characters, at least one letter and one number");
    }
}
