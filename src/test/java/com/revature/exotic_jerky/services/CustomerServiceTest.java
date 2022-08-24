package com.revature.exotic_jerky.services;

import com.revature.exotic_jerky.daos.CustomerDAO;
import com.revature.exotic_jerky.utils.custom_exceptions.InvalidCustomerException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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

    @Test (expected = InvalidCustomerException.class)
    public void test_isValidEmail_givenIncorrectEmail(){
        // Arrange
        String invalidEmail = ".testemail@originnet.";

        // Act
        sut.isValidEmail(invalidEmail);
    }

    @Test
    public void test_isValidPassword_givenCorrectPassword(){
        // Arrange
        String validPassword = "P4ssw0rd";

        // Act
        boolean flag = sut.isValidPassword(validPassword);

        // Assert
        Assert.assertTrue(flag);
    }

    @Test (expected = InvalidCustomerException.class)
    public void test_isValidPassword_givenIncorrectPassword(){
        // Arrange
        String invalidPassword = "pass";

        // Act
        sut.isValidPassword(invalidPassword);
    }

    @Test
    public void test_isValidName_givenCorrectName(){
        // Arrange
        String validName = "George";

        // Act
        boolean flag = sut.isValidName(validName);

        // Assert
        Assert.assertTrue(flag);
    }

    @Test (expected = InvalidCustomerException.class)
    public void test_isValidName_givenIncorrectName(){
        // Arrange
        String invalidName = "G e0rge32";

        // Act
        sut.isValidName(invalidName);
    }

    @Test
    public void test_isValidAddress_givenCorrectAddress(){
        // Arrange
        String validAddress = "32 Main Ave";

        // Act
        boolean flag = sut.isValidAddress(validAddress);

        // Assert
        Assert.assertTrue(flag);
    }

    @Test (expected = InvalidCustomerException.class)
    public void test_isValidAddress_givenIncorrectAddress(){
        // Arrange
        String invalidAddress = " Main Ave";

        // Act
        sut.isValidAddress(invalidAddress);
    }

    @Test
    public void test_isValidState_givenCorrectState(){
        // Arrange
        String validState = "NY";

        // Act
        boolean flag = sut.isValidState(validState);

        // Assert
        Assert.assertTrue(flag);
    }

    @Test (expected = InvalidCustomerException.class)
    public void test_isValidState_givenIncorrectState(){
        // Arrange
        String invalidState = "NU";

        // Act
        sut.isValidState(invalidState);
    }

}