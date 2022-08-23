package com.revature.exotic_jerky.models;

public class Orders {
    private String id, created, customer_id, store_id;
    private float total;

    // Non-Default Constructor
    public Orders(String id, String created, String customer_id, String store_id, float total) {
        this.id = id;
        this.created = created;
        this.customer_id = customer_id;
        this.store_id = store_id;
        this.total = total;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
}
