package com.revature.exotic_jerky.models;

public class Product {
    private String id, category, name, description, store_id;
    private float price;

    public Product(String id, String category, String name, String description, String store_id, float price) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.description = description;
        this.store_id = store_id;
        this.price = price;
    }
}
