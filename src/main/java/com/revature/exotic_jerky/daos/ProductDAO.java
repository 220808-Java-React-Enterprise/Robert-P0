package com.revature.exotic_jerky.daos;

import com.revature.exotic_jerky.models.Product;
import com.revature.exotic_jerky.utils.custom_exceptions.InvalidSQLException;
import com.revature.exotic_jerky.utils.database.ConnectionFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ProductDAO implements CrudDAO<Product>{
    @Override
    public void save(Product obj) throws IOException {

    }

    @Override
    public void update(Product obj) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public void getById(String id) {

    }

    @Override
    public List<Product> getAll() {
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
                        rs.getString("description"), rs.getString("store_id"), rs.getByte("quantity"), rs.getFloat("price")));
            }
        } catch (SQLException e){
            throw new InvalidSQLException("Error getting product by category");
        }
        return products;
    }
}
