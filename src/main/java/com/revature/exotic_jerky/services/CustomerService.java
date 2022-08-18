package com.revature.exotic_jerky.services;

import com.revature.exotic_jerky.daos.CustomerDAO;
import com.revature.exotic_jerky.models.Customer;
import com.revature.exotic_jerky.utils.custom_exceptions.InvalidCustomerException;
import com.revature.exotic_jerky.utils.custom_exceptions.InvalidSQLException;

public class CustomerService {
    private final CustomerDAO customerDAO;

    // Pre:
    // Post:
    // Purpose:
    public CustomerService(CustomerDAO customerDAO){
        this.customerDAO = customerDAO;
    }

    // Pre:
    // Post:
    // Purpose:
    public void signUp(Customer customer){
        customerDAO.save(customer);
    }

    public Customer login(String email, String password){
        Customer customer = customerDAO.getByEmailAndPassword(email, password);
        if (customer == null)
            throw new InvalidCustomerException("Email or Password is Wrong!");
        return customer;
    }

    // Pre:
    // Post:
    // Purpose:
    public void updateAccount(Customer customer){
        customerDAO.update(customer);
    }
    
    // Pre:
    // Post:
    // Purpose:
    public boolean isDuplicateEmail(String email){
        if (customerDAO.getByEmail(email) != null) return true;
        else return false;
    }

    // Pre:
    // Post:
    // Purpose:
    public boolean isValidName(String name){
        if (!name.matches("^[\\p{L} .'-]+$")) throw new InvalidCustomerException("\nInvalid format!");
        return true;
    }

    // Pre:
    // Post:
    // Purpose:
    public boolean isValidEmail(String email){
        if (!email.matches("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$"))
            throw new InvalidCustomerException("\nInvalid email format!");
        return true;
    }

    // Pre:
    // Post:
    // Purpose:
    public boolean isValidAddress(String address){
        if (!address.matches("^(\\d{1,}) [a-zA-Z0-9\\s]+(\\,)? [a-zA-Z]+(\\,)?$"))
            throw new InvalidCustomerException("Invalid address! Try again...");
        return true;
    }

    // Pre:
    // Post:
    // Purpose:
    public boolean isValidState(String state){
        if (!state.matches("^(?-i:A[LKSZRAEP]|C[AOT]|D[EC]|F[LM]|G[AU]|HI|I[ADLN]|K[SY]|LA|M[ADEHINOPST]|N[CDEHJMVY]|O[HKR]|P[ARW]|RI|S[CD]|T[NX]|UT|V[AIT]|W[AIVY])$"))
            throw new InvalidCustomerException("\nInvalid state abbreviation");
        return true;
    }

    // Pre:
    // Post:
    // Purpose:
    public boolean isValidZip(String zip){
        if (!zip.matches("^[0-9]{5}(?:-[0-9]{4})?$"))
            throw new InvalidCustomerException("\nInvalid zip code format");
        return true;
    }

    // Pre:
    // Post:
    // Purpose:
    public boolean isValidPhone(String phone){
        if (!phone.matches("^(\\d{3}[- .]?){2}\\d{4}$"))
            throw new InvalidCustomerException("\nInvalid phone number format");
        return true;
    }

    // Pre:
    // Post:
    // Purpose:
    public boolean isValidPassword(String pass){
        if (!pass.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$"))
            throw new InvalidCustomerException("\nInvalid password! Minimum eight characters, at least one letter and one number");
        return true;
    }
}
