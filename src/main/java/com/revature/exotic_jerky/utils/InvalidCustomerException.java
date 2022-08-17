package com.revature.exotic_jerky.utils;

public class InvalidCustomerException extends RuntimeException{

    public InvalidCustomerException(String m){
        super(m);
    }
}