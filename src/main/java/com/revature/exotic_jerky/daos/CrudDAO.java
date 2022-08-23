package com.revature.exotic_jerky.daos;

import com.revature.exotic_jerky.models.Product;

import java.io.IOException;
import java.util.List;

public interface CrudDAO<T> {
    void save(T obj) throws IOException;
    void update(T obj);
    void delete(String id);
    Product getById(String id);
    List<T> getAll();
}