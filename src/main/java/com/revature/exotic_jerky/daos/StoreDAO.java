package com.revature.exotic_jerky.daos;

import com.revature.exotic_jerky.models.Product;
import com.revature.exotic_jerky.models.Store;
import com.revature.exotic_jerky.utils.custom_exceptions.InvalidSQLException;
import com.revature.exotic_jerky.utils.database.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StoreDAO implements CrudDAO<Store>{
    @Override
    public void save(Store store){
        try (Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("INSERT INTO stores (id, name, email, address," +
                    "city, state, zip, phone) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, store.getId()); ps.setString(2, store.getName());
            ps.setString(3, store.getEmail()); ps.setString(4, store.getAddress());
            ps.setString(5, store.getCity()); ps.setString(6, store.getState());
            ps.setString(7, store.getZip()); ps.setString(8, store.getPhone());
            ps.executeUpdate();
        } catch (SQLException e){
            throw new InvalidSQLException("Error trying to save store");
        }
    }

    @Override
    public void update(Store obj) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public Product getById(String id) {

        return null;
    }

    @Override
    public List<Store> getAll() {
        List<Store> stores = new ArrayList<>();
        try(Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT * FROM stores");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) stores.add(new Store(rs.getString("id"), rs.getString("name"), rs.getString("email"),
                    rs.getString("address"), rs.getString("city"), rs.getString("state"), rs.getString("zip"), rs.getString("zip")));

            return stores;
        } catch (SQLException e){
            throw new InvalidSQLException("Error trying to grab stores");
        }
    }

    public String getByAddress(String address){
        try (Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT (address) FROM stores WHERE address = ?");
            ps.setString(1, address);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) return rs.getString("address");

        } catch (SQLException e){
            throw new InvalidSQLException("Error getting user");
        }
        return null;
    }

    public Store getByEmail(String email){
        try (Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT * FROM stores WHERE email = ?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) return new Store(rs.getString("id"), rs.getString("name"), rs.getString("email"), rs.getString("address"), rs.getString("city"),
                    rs.getString("state"), rs.getString("zip"), rs.getString("phone"));
        } catch (SQLException e){
            throw new InvalidSQLException("Error getting user");
        }
        return null;
    }
}
