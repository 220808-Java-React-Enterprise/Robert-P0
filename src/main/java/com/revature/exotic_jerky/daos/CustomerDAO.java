package com.revature.exotic_jerky.daos;

import com.revature.exotic_jerky.models.Customer;
import com.revature.exotic_jerky.utils.custom_exceptions.InvalidSQLException;
import com.revature.exotic_jerky.utils.database.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CustomerDAO implements CrudDAO<Customer>{

    @Override
    public void save(Customer customer){
        try (Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("INSERT INTO customers (id, fname, lname, email, password, " +
                    "address, city, state, zip, phone, role) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, customer.getId()); ps.setString(2, customer.getfName());
            ps.setString(3, customer.getlName()); ps.setString(4, customer.getEmail());
            ps.setString(5, customer.getPassword()); ps.setString(6, customer.getAddress());
            ps.setString(7, customer.getCity()); ps.setString(8, customer.getState());
            ps.setString(9, customer.getZip()); ps.setString(10, customer.getPhone());
            ps.setString(11, customer.getRole());
            ps.executeUpdate();
        } catch (SQLException e){
            throw new InvalidSQLException("Error trying to save to database");
        }
    }

    @Override
    public void update(Customer customer) {
        try (Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("UPDATE customers SET fname = ?, lname = ?, email = ?," +
                    "password = ?, address = ?, city = ?, state = ?, zip = ?, phone = ?, role = ? WHERE id = ?");
            ps.setString(1, customer.getfName()); ps.setString(2, customer.getlName());
            ps.setString(3, customer.getEmail()); ps.setString(4, customer.getPassword());
            ps.setString(5, customer.getAddress()); ps.setString(6, customer.getCity());
            ps.setString(7, customer.getState()); ps.setString(8, customer.getZip());
            ps.setString(9, customer.getPhone()); ps.setString(10, customer.getRole());
            ps.setString(11, customer.getId());
            ps.executeUpdate();
        } catch (SQLException e){
            throw new InvalidSQLException("Error tyring to update database");
        }
    }

    @Override
    public void delete(String id) {
        try (Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("DELETE FROM customers WHERE id = ?");
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e){
            throw new InvalidSQLException("Error tyring to delete from database");
        }
    }

    @Override
    public void getById(String id) {

    }

    @Override
    public List getAll() {
        return null;
    }

    public String getByEmail(String email){
        try (Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT (email) FROM customers WHERE email = ?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) return rs.getString("email");

        } catch (SQLException e){
            throw new InvalidSQLException("Error getting user");
        }
        return null;
    }

    public Customer getByEmailAndPassword(String email, String password){
        try (Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT * FROM customers WHERE email = ? AND password = ?");
            ps.setString(1, email); ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) return new Customer(rs.getString("id"), rs.getString("email"), rs.getString("password"), rs.getString("fname"),
                    rs.getString("lname"), rs.getString("address"), rs.getString("city"), rs.getString("state"), rs.getString("zip"),
                    rs.getString("phone"),rs.getString("role"));
        } catch (SQLException e){
            throw new InvalidSQLException("Error finding user");
        }
        return null;
    }
}