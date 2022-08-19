package com.revature.exotic_jerky.daos;

import com.revature.exotic_jerky.models.Store;
import com.revature.exotic_jerky.utils.custom_exceptions.InvalidSQLException;
import com.revature.exotic_jerky.utils.database.ConnectionFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class StoreDAO implements CrudDAO<Store>{
    @Override
    public void save(Store store){
        try (Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("INSERT INTO stores (id, name, address," +
                    "city, state, zip, phone) VALUES (?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, store.getId()); ps.setString(2, store.getName());
            ps.setString(3, store.getAddress()); ps.setString(4, store.getCity());
            ps.setString(5, store.getState()); ps.setString(6, store.getZip());
            ps.setString(7, store.getPhone());
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
    public void getById(String id) {

    }

    @Override
    public List<Store> getAll() {
        return null;
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
}
