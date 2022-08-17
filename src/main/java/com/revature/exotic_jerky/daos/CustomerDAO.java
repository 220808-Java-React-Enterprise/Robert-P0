package com.revature.exotic_jerky.daos;

import com.revature.exotic_jerky.models.Customer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO implements CrudDAO<Customer>{
    private final String path = "src/main/resources/com/revature/exotic-jerky/db/customer.txt";
    @Override
    public void save(Customer cust){
        try{
            File customers = new File(path);
            FileWriter writer = new FileWriter(customers, true);
            writer.write(cust.toFileString());
            writer.close();
        } catch (IOException e){
            throw new RuntimeException("\nError occurred while trying to write to file.");
        }
    }

    @Override
    public void update(Customer obj) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public void getById(String id) {

    }

    @Override
    public List getAll() {
        List<Customer> customerList = new ArrayList<>();

        try{
            BufferedReader br = new BufferedReader(new FileReader(path));
            String data = "";

            while ((data = br.readLine()) != null){
                String[] c = data.split(":");
                Customer customer = new Customer();
            }
        }catch (FileNotFoundException e){
            throw new RuntimeException("Failed to retrieve file.");
        }catch (IOException e){
            throw new RuntimeException("Failed to read file.");
        }
        return customerList;
    }
}