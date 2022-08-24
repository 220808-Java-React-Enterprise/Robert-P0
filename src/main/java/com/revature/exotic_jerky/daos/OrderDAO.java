package com.revature.exotic_jerky.daos;

import com.revature.exotic_jerky.models.Orders;
import com.revature.exotic_jerky.models.Product;
import com.revature.exotic_jerky.utils.custom_exceptions.InvalidSQLException;
import com.revature.exotic_jerky.utils.database.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class OrderDAO implements CrudDAO<Orders>{
    @Override
    public void save(Orders order){
    }

    public void save(Orders order, String storeID){
        try(Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("INSERT INTO orders (id, grand_total, created, customer_id, store_id) VALUES (?, ?, ?, ?, ?)");
            ps.setString(1, order.getId()); ps.setFloat(2, order.getTotal());
            ps.setString(3, order.getCreated()); ps.setString(4, order.getCustomer_id());
            ps.setString(5, storeID);
            ps.executeUpdate();
        } catch (SQLException e){
            throw new InvalidSQLException("Error trying to save cart to database");
        }
    }
    public void save(String ID, byte quantity, float total, String orderID, String productID){
        try(Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("INSERT INTO orders_jct (id, quantity, total, order_id, product_id) VALUES (?, ?, ?, ?, ?)");
            ps.setString(1, ID); ps.setByte(2, quantity);
            ps.setFloat(3, total); ps.setString(4, orderID);
            ps.setString(5, productID);
            ps.executeUpdate();
        } catch (SQLException e){
            throw new InvalidSQLException("Error trying to save cart to database");
        }
    }

    @Override
    public void update(Orders obj) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public Product getById(String id) {

        return null;
    }

    @Override
    public List<Orders> getAll() {
        return null;
    }

    public Map<String, List<String>> getOrderHistory(String customerID, String sortType, boolean ascending){
        Map<String, List<String>> cart;

        if (ascending) cart = new TreeMap<>();
        else cart = new TreeMap<>(Collections.reverseOrder());

        List<String> details;

        try (Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT products.name, orders_jct.total, orders_jct.quantity, " +
                    "orders.created, orders.grand_total, orders.id FROM orders JOIN orders_jct ON orders.id = orders_jct.order_id " +
                    "JOIN products ON orders_jct.product_id = products.id WHERE orders.customer_id = ?");
            ps.setString(1, customerID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                details = new ArrayList<>();
                details.add(rs.getString("name")); details.add(String.valueOf(rs.getByte("quantity")));
                details.add(String.valueOf(rs.getFloat("total"))); details.add(rs.getString("created"));
                details.add(String.valueOf(rs.getFloat("grand_total"))); details.add(rs.getString("id"));

                cart.put(rs.getString(sortType), details);
            }
        } catch (SQLException e){
            throw new InvalidSQLException("Error trying to get customers order");
        }
        return cart;
    }

    public Map<String, List<String>> getOrderHistory(String orderID){
        Map<String, List<String>> cart = new TreeMap<>();
        List<String> details;

        try (Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT products.name, orders_jct.quantity, orders_jct.total, orders.grand_total " +
                    "FROM orders JOIN orders_jct ON orders.id = orders_jct.order_id JOIN products ON orders_jct.product_id = products.id " +
                    "WHERE orders.id = ?");
            ps.setString(1, orderID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                details = new ArrayList<>();
                details.add(rs.getString("name")); details.add(String.valueOf(rs.getByte("quantity")));
                details.add(String.valueOf(rs.getFloat("total"))); details.add(String.valueOf(rs.getFloat("grand_total")));
                cart.put(rs.getString("name"), details);
            }
        } catch (SQLException e){
            throw new InvalidSQLException("Error trying to get customers cart");
        }
        return cart;
    }

    public Map<String, List<String>> getStoreHistory(String storeID, String sortType, boolean ascending){
        Map<String, List<String>> history = new TreeMap<>();

        if (ascending) history = new TreeMap<>();
        else history = new TreeMap<>(Collections.reverseOrder());

        List<String> details;

        try(Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT products.name, orders_jct.quantity, orders_jct.total, orders.created, orders.grand_total, " +
                    "orders.id FROM orders JOIN orders_jct ON orders.id = orders_jct.order_id JOIN products ON orders_jct.product_id = products.id " +
                    "WHERE orders.store_id = ?");
            ps.setString(1, storeID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                details = new ArrayList<>();
                details.add(rs.getString("name")); details.add(String.valueOf(rs.getByte("quantity")));
                details.add(String.valueOf(rs.getFloat("total"))); details.add(rs.getString("created"));
                details.add(String.valueOf(rs.getFloat("grand_total"))); details.add(rs.getString("id"));

                history.put(rs.getString(sortType), details);
            }
        }
        catch (SQLException e){
            throw new InvalidSQLException("Error tyring to get store order history");
        }
        return history;
    }
}
