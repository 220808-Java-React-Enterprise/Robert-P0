package com.revature.exotic_jerky.ui;

import com.revature.exotic_jerky.models.Cart;
import com.revature.exotic_jerky.models.Customer;
import com.revature.exotic_jerky.services.CartService;

public class CartMenu implements IMenu{
    private final Customer customer;
    private final Cart cart;
    private final CartService cartService;

    public CartMenu(Customer customer, Cart cart, CartService cartService) {
        this.customer = customer;
        this.cart = cart;
        this.cartService = cartService;
    }

    @Override
    public void start() {

    }

    private void getCart(){

    }
}
