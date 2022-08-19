package com.revature.exotic_jerky.services;

import com.revature.exotic_jerky.daos.StoreDAO;
import com.revature.exotic_jerky.models.Store;

public class StoreService {
    private final StoreDAO storeDAO;

    public StoreService(StoreDAO storeDAO) {
        this.storeDAO = storeDAO;
    }

    public void saveStore(Store store){
        storeDAO.save(store);
    }

    public boolean isDuplicateStore(String address){
        if (storeDAO.getByAddress(address) != null) return true;
        else return false;
    }
}
