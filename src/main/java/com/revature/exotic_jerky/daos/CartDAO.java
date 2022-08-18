package com.revature.exotic_jerky.daos;

import com.revature.exotic_jerky.models.Cart;
import com.revature.exotic_jerky.utils.custom_exceptions.InvalidSQLException;
import com.revature.exotic_jerky.utils.database.ConnectionFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CartDAO implements CrudDAO<Cart>{
    @Override
    public void save(Cart obj) throws IOException {

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
}
