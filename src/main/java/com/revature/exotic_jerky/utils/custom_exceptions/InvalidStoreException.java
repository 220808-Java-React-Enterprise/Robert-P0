package com.revature.exotic_jerky.utils.custom_exceptions;

public class InvalidStoreException extends RuntimeException{
    public InvalidStoreException(String m){
        super(m);
    }
}
