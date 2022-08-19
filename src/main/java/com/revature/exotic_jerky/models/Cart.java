package com.revature.exotic_jerky.models;

import java.util.Date;

public class Cart {
    private String id, customerID;
    private Date date;
    private float total;

    // Pre:
    // Post:
    // Purpose:
    public Cart(String id, float total, Date date, String customerID) {
        this.id = id;
        this.total = total;
        this.date = new Date();
        this.customerID = customerID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
}
