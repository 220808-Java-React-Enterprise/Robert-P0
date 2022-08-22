package com.revature.exotic_jerky.services;

import com.revature.exotic_jerky.daos.StoreDAO;
import com.revature.exotic_jerky.models.Store;
import com.revature.exotic_jerky.utils.custom_exceptions.InvalidStoreException;

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
    public Store getByEmail(String email){
        Store store = storeDAO.getByEmail(email);
        if (store == null)
            throw new InvalidStoreException("Could not located store!");
        return store;
    }
}
