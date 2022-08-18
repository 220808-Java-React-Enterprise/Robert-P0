package com.revature.exotic_jerky.services;

import com.revature.exotic_jerky.daos.ProductDAO;
import com.revature.exotic_jerky.models.Cart;
import com.revature.exotic_jerky.models.Product;

import java.util.List;

public class ProductService {
    private final ProductDAO productDAO;

    // Pre:
    // Post:
    // Purpose:
    public ProductService(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public List<Product> getProduct(String category){
        return productDAO.getAllByCategory(category);
    }
}
