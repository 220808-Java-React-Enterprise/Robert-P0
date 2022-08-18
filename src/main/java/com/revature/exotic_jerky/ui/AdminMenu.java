package com.revature.exotic_jerky.ui;

import com.revature.exotic_jerky.models.Customer;
import com.revature.exotic_jerky.services.CustomerService;

public class AdminMenu implements IMenu{
    private  final Customer customer;
    private final CustomerService customerService;

    public AdminMenu(Customer customer, CustomerService customerService) {
        this.customer = customer;
        this.customerService = customerService;
    }

    @Override
    public void start() {

    }
}
