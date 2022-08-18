package com.revature.exotic_jerky.daos;

import com.revature.exotic_jerky.models.Cart;
import com.revature.exotic_jerky.models.Product;
import com.revature.exotic_jerky.utils.custom_exceptions.InvalidSQLException;
import com.revature.exotic_jerky.utils.database.ConnectionFactory;

import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.UUID;

public class CartDAO implements CrudDAO<Cart>{
    @Override
    public void save(Cart cart){
        try(Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("INSERT INTO carts (id, total, created, customer_id) VALUES (?, ?, ?, ?)");
            ps.setString(1, cart.getId()); ps.setFloat(2, cart.getTotal());
            ps.setString(3, String.valueOf(cart.getDate())); ps.setString(4, cart.getCustomerID());
            ps.executeUpdate();
        } catch (SQLException e){
            throw new InvalidSQLException("Error trying to save cart to database");
        }
    }

    @Override
    public void update(Cart obj) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public void getById(String id) {

    }

    @Override
    public List<Cart> getAll() {
        return null;
    }

    public Cart getCartByCustomerID(String customerID){
        try (Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT * FROM carts WHERE cusomter_id = ?");
            ps.setString(1, customerID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) return new Cart(rs.getString("id"), rs.getString("customer_id"), rs.getDate("created"), rs.getFloat("total"));
        } catch (SQLException e){
        }
        return null;
    }

    public void addToCart(Cart cart, byte quantity, Product product){
        try (Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("INSERT INTO carts_jct (id, quantity, cart_id, product_id) VALUES (?, ?, ?, ?)");
            ps.setString(1, UUID.randomUUID().toString()); ps.setByte(2, quantity);
            ps.setString(3, cart.getId()); ps.setString(4, product.getId());
            ps.executeUpdate();
        } catch (SQLException e){
            throw new InvalidSQLException("Error trying to save to cart");
        }
    }
}
