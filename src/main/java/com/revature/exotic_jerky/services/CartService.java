package com.revature.exotic_jerky.services;

import com.revature.exotic_jerky.daos.CartDAO;
import com.revature.exotic_jerky.models.Cart;
import com.revature.exotic_jerky.models.Product;

import java.util.Date;
import java.util.List;
import java.util.Map;
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
            cart = new Cart(UUID.randomUUID().toString(), 0.00f, new Date(), customerID);
        }
        return cart;
    }

    public void saveCart(Cart cart){
        cartDAO.save(cart);
    }

    public void updateCartTotal(Cart cart){
        cartDAO.updateTotal(cart);
    }

    public void addToCart(Cart cart, byte quantity, float total, Product product){
        String[] list = cartDAO.hasExistingItem(cart, product);

        if (list != null){
            byte newQuantity = (byte) (quantity + Byte.parseByte(list[1]));
            float newTotal = total + Float.parseFloat(list[2]);
            cartDAO.updateExistingItem(list[0], cart, newQuantity, newTotal);
        }
        else
            cartDAO.addToCart(cart, quantity, total, product);
    }

    public void deleteCartByCustomerID(String customerID){
        cartDAO.delete(customerID);
    }

    public void deleteCartJCT(String cartID){
        cartDAO.deleteCartJCTByCartID(cartID);
    }

    public Map<String, List<String>> getCheckOutCart(String customerID){
        return cartDAO.getCheckOutCart(customerID);
    }
}
