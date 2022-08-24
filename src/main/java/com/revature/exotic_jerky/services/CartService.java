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

    // Non-Default Constructor
    public CartService(CartDAO cartDAO) {
        this.cartDAO = cartDAO;
    }

    // Pre:
    // Post:
    // Purpose:
    public Cart hasExistingCart(String customerID){
        Cart cart = cartDAO.getCartByCustomerID(customerID);
        if (cart == null){
            cart = new Cart(UUID.randomUUID().toString(), 0.00f, new Date(), customerID);
        }
        return cart;
    }

    // Pre:
    // Post:
    // Purpose:
    public boolean isExistingCart(String customerID){
        return cartDAO.getCartByCustomerID(customerID) != null;
    }

    public void saveCart(Cart cart, String storeID){
        cartDAO.save(cart, storeID);
    }

    public void updateCartTotal(Cart cart){
        cartDAO.updateTotal(cart);
    }

    public void updateCartQuantityAndTotal(Cart cart, String productID, byte quantity){
        addToCart(cart, quantity, productID);
    }

    public void updateCartID(String customerID, String cartID){
        cartDAO.updateCustomerID(customerID, cartID);
    }

    public void addToCart(Cart cart, byte quantity, String productID){
        String[] list = cartDAO.hasExistingItem(cart, productID);

        if (list != null){
            byte newQuantity = (byte) (quantity + Byte.parseByte(list[1]));
            cart.setTotal(cart.getTotal() + Float.parseFloat(list[2]));
            cartDAO.updateExistingItem(list[0], cart, newQuantity);
        }
        else
            cartDAO.addToCart(cart, quantity, productID);
    }

    public void deleteCartByCustomerID(String customerID){
        cartDAO.delete(customerID);
    }

    public void deleteCartJCT(String cartID){
        cartDAO.deleteCartJCTByCartID(cartID);
    }

    public void deleteCartJCT(String cartID, String productID){
        cartDAO.deleteCartJCTByCartIDAndProductID(cartID, productID);
    }

    public Map<String, List<String>> getCheckOutCart(String customerID){
        return cartDAO.getCheckOutCart(customerID);
    }
}
