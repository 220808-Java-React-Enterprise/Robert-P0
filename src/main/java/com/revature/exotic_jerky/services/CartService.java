package com.revature.exotic_jerky.services;

import com.revature.exotic_jerky.daos.CartDAO;
import com.revature.exotic_jerky.models.Cart;
import com.revature.exotic_jerky.models.Product;

import java.util.Date;
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
        if (cart == null){
            cart = new Cart(UUID.randomUUID().toString(), customerID, new Date());
            saveCart(cart);
        }

        return cart;
    }

    public void saveCart(Cart cart){
        cartDAO.save(cart);
    }

    public void addToCart(Cart cart, byte quantity, Product product){
        cartDAO.addToCart(cart, quantity, product);
    }
}
