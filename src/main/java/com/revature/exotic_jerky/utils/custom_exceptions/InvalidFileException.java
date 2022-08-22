package com.revature.exotic_jerky.utils.custom_exceptions;

import java.io.IOException;

public class InvalidFileException extends RuntimeException {
    public InvalidFileException(String m){
        super(m);
    }
}
