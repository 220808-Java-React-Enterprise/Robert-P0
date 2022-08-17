package com.revature.exotic_jerky.daos;

import java.io.IOException;
import java.util.List;

public class ProductDAO implements CrudDAO{
    String path = "src/main/resources/com/revature/exotic-jerky/product.txt";

    @Override
    public void save(Object obj) throws IOException {

    }

    @Override
    public void update(Object obj) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public void getById(String id) {

    }

    @Override
    public List getAll() {
        return null;
    }
}
