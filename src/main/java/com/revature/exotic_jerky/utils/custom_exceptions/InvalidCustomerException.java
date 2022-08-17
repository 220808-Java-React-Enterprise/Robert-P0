package com.revature.exotic_jerky.utils.custom_exceptions;

public class InvalidCustomerException extends RuntimeException{

    public InvalidCustomerException(String m){
        super(m);
    }
}