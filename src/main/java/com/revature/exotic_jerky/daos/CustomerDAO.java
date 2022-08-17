package com.revature.exotic_jerky.daos;

import com.revature.exotic_jerky.models.Customer;
import com.revature.exotic_jerky.utils.database.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class CustomerDAO implements CrudDAO<Customer>{

    @Override
    public void save(Customer cust){
        try (Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("INSERT INTO customers (id, fname, lname, email, password, " +
                    "address, city, state, zip, phone, role) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, cust.getId()); ps.setString(2, cust.getfName());
            ps.setString(3, cust.getlName()); ps.setString(4, cust.getEmail());
            ps.setString(5, cust.getPassword()); ps.setString(6, cust.getAddress());
            ps.setString(7, cust.getCity()); ps.setString(8, cust.getState());
            ps.setString(9, cust.getZip()); ps.setString(10, cust.getPhone());
            ps.setString(11, cust.getRole());
            ps.executeUpdate();
        } catch (SQLException e){
            throw new RuntimeException("Error trying to save to database");
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
        return null;
    }
}