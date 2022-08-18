package com.revature.exotic_jerky.models;

import java.util.Date;

public class Cart {
    private String id, customerID;
    private Date date;
    private float total;

    // Pre:
    // Post:
    // Purpose:
    public Cart(String id, String customerID, float total) {
        this.id = id;
        this.customerID = customerID;
        this.date = new Date();
        this.total = total;
    }

    // Pre:
    // Post:
    // Purpose:
    public Cart(String id, String customerID, Date date, float total) {
        this.id = id;
        this.customerID = customerID;
        this.date = date;
        this.total = total;
    }
}
