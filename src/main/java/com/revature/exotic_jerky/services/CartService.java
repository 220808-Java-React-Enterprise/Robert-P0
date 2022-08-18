package com.revature.exotic_jerky.services;

import com.revature.exotic_jerky.daos.CartDAO;
import com.revature.exotic_jerky.models.Cart;
import com.revature.exotic_jerky.utils.custom_exceptions.InvalidCartException;

import java.util.UUID;

public class CartService {
    private final CartDAO cartDAO;

    // Pre:
    // Post:
    // Purpose:
    public CartService(CartDAO cartDAO) {
        this.cartDAO = cartDAO;
    }

    public Cart hasExistingCart(String customerID){
        Cart cart = cartDAO.getCartByCustomerID(customerID);
        if (cart == null)
            return new Cart(UUID.randomUUID().toString(), customerID, 0.0f);
        return cart;
    }
}
