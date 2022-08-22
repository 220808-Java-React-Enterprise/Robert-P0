package com.revature.exotic_jerky.daos;

import com.revature.exotic_jerky.models.Cart;
import com.revature.exotic_jerky.models.Product;
import com.revature.exotic_jerky.utils.custom_exceptions.InvalidSQLException;
import com.revature.exotic_jerky.utils.database.ConnectionFactory;

import java.sql.*;
import java.util.*;
import java.util.Date;

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
    public void update(Cart cart) {

    }

    @Override
    public void delete(String id) {
        try (Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("DELETE FROM carts WHERE customer_id = ?");
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
    public List<Cart> getAll() {
        return null;
    }

    public Cart getCartByCustomerID(String customerID){
        try (Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT * FROM carts WHERE customer_id = ?");
            ps.setString(1, customerID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) return new Cart(rs.getString("id"), rs.getFloat("total"), new Date(), rs.getString("customer_id"));
        } catch (SQLException e){
            throw new InvalidSQLException("Error getting customer cart");
        }
        return null;
    }

    public void addToCart(Cart cart, byte quantity, float total, Product product){
        try (Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("INSERT INTO carts_jct (id, quantity, total, cart_id, product_id) VALUES (?, ?, ?, ?, ?)");
            ps.setString(1, UUID.randomUUID().toString()); ps.setByte(2, quantity);
            ps.setFloat(3, total); ps.setString(4, cart.getId());
            ps.setString(5, product.getId());
            ps.executeUpdate();
        } catch (SQLException e){
            throw new InvalidSQLException("Error trying to save to cart");
        }
    }

    public void updateTotal(Cart cart) {
        try (Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("UPDATE carts SET total = ? WHERE customer_id = ?");
            ps.setFloat(1, cart.getTotal()); ps.setString(2, cart.getCustomerID());
            ps.executeUpdate();
        } catch (SQLException e){
            throw new InvalidSQLException("Error updating cart");
        }
    }

    public String[] hasExistingItem(Cart cart, Product product){
        try(Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT id, quantity, total FROM carts_jct WHERE cart_id = ? AND product_id = ?");
            ps.setString(1, cart.getId()); ps.setString(2, product.getId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                String[] list = new String[3];
                list[0] = rs.getString("id");
                list[1] = String.valueOf(rs.getByte("quantity"));
                list[2] = String.valueOf(rs.getFloat("total"));
                return list;
            }
        } catch (SQLException e){
            throw new InvalidSQLException("Error getting existing product from cart.");
        }
        return null;
    }

    public void updateExistingItem(String id, Cart cart, byte quantity, float total){
        try (Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("UPDATE carts_jct SET quantity = ?, total = ? WHERE id = ?");
            ps.setByte(1, quantity); ps.setFloat(2, total);
            ps.setString(3, id);
            ps.executeUpdate();
        } catch (SQLException e){
            throw new InvalidSQLException("Error while updating existing cart");
        }
    }

    public Map<String, List<String>> getCheckOutCart(String customerID){
        Map<String, List<String>> cart = new TreeMap<>();
        List<String> details;

        try (Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT products.name, products.price, carts_jct.quantity, " +
                    "carts_jct.total FROM carts JOIN carts_jct ON carts.id = carts_jct.cart_id JOIN products ON " +
                    "carts_jct.product_id = products.id WHERE carts.customer_id = ?");
            ps.setString(1, customerID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                details = new ArrayList<>();
                details.add(rs.getString("name")); details.add(String.valueOf(rs.getFloat("price")));
                details.add(String.valueOf(rs.getByte("quantity"))); details.add(String.valueOf(rs.getFloat("total")));
                cart.put(rs.getString("name"), details);
            }
        } catch (SQLException e){
            throw new InvalidSQLException("Error trying to get customers cart");
        }
        return cart;
    }

    public void deleteCartJCTByCartID(String cartID){
        try (Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("DELETE FROM carts_jct WHERE cart_id = ?");
            ps.setString(1, cartID);
            ps.executeUpdate();
        } catch (SQLException e){
            throw new InvalidSQLException("Error tyring to delete from database");
        }
    }
}
