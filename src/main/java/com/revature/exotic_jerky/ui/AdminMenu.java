package com.revature.exotic_jerky.ui;

import com.revature.exotic_jerky.models.Customer;
import com.revature.exotic_jerky.services.CustomerService;
import com.revature.exotic_jerky.services.StoreService;

public class AdminMenu implements IMenu{
    private  final Customer customer;
    private final StoreService storeService;
    private final CustomerService customerService;

    public AdminMenu(Customer customer, StoreService storeService, CustomerService customerService) {
        this.customer = customer;
        this.storeService = storeService;
        this.customerService = customerService;
    }

    @Override
    public void start() {

    }
}
