package com.revature.exotic_jerky.services;

import com.revature.exotic_jerky.daos.OrderDAO;
import com.revature.exotic_jerky.models.Orders;

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
    public Map<String, List<String>> getOrderHistory(String ID, String sortType, boolean ascending, String role) {
        if (role.equals("ADMIN")) return orderDAO.getStoreHistory(ID, sortType, ascending);
        return orderDAO.getOrderHistory(ID, sortType, ascending);
    }

    public Map<String, List<String>> getOrderHistory(String orderID){
        return orderDAO.getOrderHistory(orderID);
    }
}
