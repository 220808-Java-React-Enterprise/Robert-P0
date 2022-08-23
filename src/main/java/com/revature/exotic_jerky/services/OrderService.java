package com.revature.exotic_jerky.services;

import com.revature.exotic_jerky.daos.OrderDAO;
import com.revature.exotic_jerky.models.Orders;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class OrderService {
    private final OrderDAO orderDAO;

    // Pre:
    // Post:
    // Purpose:
    public OrderService(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    // Pre:
    // Post:
    // Purpose:
    public void saveOrder(Orders order, String storeID){
        orderDAO.save(order, storeID);
    }

    // Pre:
    // Post:
    // Purpose:
    public void saveJCT(byte quantity, float total, String orderID, String productID){
        orderDAO.save(UUID.randomUUID().toString(), quantity, total, orderID, productID);
    }

    // Pre:
    // Post:
    // Purpose:
    public Map<String, List<String>> getOrderHistory(String role, String customerID, String sortType, boolean ascending) {
        return orderDAO.getOrderHistory(customerID, sortType, ascending);
    }

    public Map<String, List<String>> getOrderHistory(String customerID, String orderID){
        return orderDAO.getOrderHistory(customerID, orderID);
    }
}
