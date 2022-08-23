package com.revature.exotic_jerky.daos;

import com.revature.exotic_jerky.models.Product;
import com.revature.exotic_jerky.utils.custom_exceptions.InvalidSQLException;
import com.revature.exotic_jerky.utils.database.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ProductDAO implements CrudDAO<Product>{
    @Override
    public void save(Product product){
        try (Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("INSERT INTO products (id, category, name, price," +
                    "description, quantity, store_id) VALUES (?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, product.getId()); ps.setString(2, product.getCategory());
            ps.setString(3, product.getName()); ps.setFloat(4, product.getPrice());
            ps.setString(5, product.getDescription()); ps.setShort(6, product.getQuantity());
            ps.setString(7, product.getStoreID());
            ps.executeUpdate();
        } catch (SQLException e){
            throw new InvalidSQLException("Error tyring to update database");
        }
    }

    @Override
    public void update(Product product) {
        try (Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("UPDATE products SET category = ?, name = ?, price = ?," +
                    "description = ?, quantity = ? WHERE id = ?");
            ps.setString(1, product.getCategory()); ps.setString(2, product.getName());
            ps.setFloat(3, product.getPrice()); ps.setString(4, product.getDescription());
            ps.setByte(5, product.getQuantity()); ps.setString(6, product.getId());
            ps.executeUpdate();
        } catch (SQLException e){
            throw new InvalidSQLException("Error tyring to update database");
        }
    }

    public void updateProductQuantity(byte quantity, String productID){
        try (Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("UPDATE products SET quantity = ? WHERE id = ?");
            ps.setFloat(1, quantity); ps.setString(2, productID);
            ps.executeUpdate();
        } catch (SQLException e){
            throw new InvalidSQLException("Error trying to update quantity");
        }
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public List<Product> getAll() {
        return null;
    }

    public Map<String, List<Product>> getAllProductSortedByCategory(){
        Map<String, List<Product>> products = new TreeMap<>();
        List<Product> p;
        String cat;

        try(Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT * FROM products");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                cat = rs.getString("category");
                if (!products.containsKey(cat))
                    products.put(rs.getString("category"), new ArrayList<>());

                p = products.get(cat);
                p.add(new Product(rs.getString("id"), rs.getString("category"), rs.getString("name"),
                        rs.getString("description"), rs.getString("store_id"), rs.getFloat("price"), rs.getByte("quantity")));
                products.put(cat, p);
            }

            return products;
        } catch (SQLException e){
            throw new InvalidSQLException("Error trying to grab stores");
        }
    }

    @Override
    public Product getById(String id) {
        try(Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT * FROM products WHERE id = ?");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return new Product(rs.getString("id"), rs.getString("category"),
                    rs.getString("name"), rs.getString("description"),
                    rs.getString("store_id"), rs.getFloat("price"), rs.getByte("quantity"));
        } catch (SQLException e){
            throw new InvalidSQLException("Error getting product");
        }
        return null;
    }

    public Map<String, Product> getAllByCategory(String category){
        Map<String, Product> products = new TreeMap<>();

        try (Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT * FROM products WHERE category = ?");
            ps.setString(1, category);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                products.put(rs.getString("name"), new Product(rs.getString("id"), rs.getString("category"), rs.getString("name"),
                        rs.getString("description"), rs.getString("store_id"), rs.getFloat("price"), rs.getByte("quantity")));
            }
        } catch (SQLException e){
            throw new InvalidSQLException("Error getting product by category");
        }
        return products;
    }
}
