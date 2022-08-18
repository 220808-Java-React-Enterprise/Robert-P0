package com.revature.exotic_jerky.services;

import com.revature.exotic_jerky.daos.CustomerDAO;
import com.revature.exotic_jerky.models.Customer;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class CustomerServiceTest {
    private CustomerService sut; // System Under Test
    private final CustomerDAO mockCustomerDAO = mock(CustomerDAO.class);

    @Before
    public void setup(){
        sut = new CustomerService(mockCustomerDAO);
    }

    @Test
    public void test_isValidEmail_givenCorrectEmail(){
        // Arrange
        String validEmail = "testemail@origin.com";

        // Act
        boolean flag = sut.isValidEmail(validEmail);

        // Assert
        Assert.assertTrue(flag);
    }

}