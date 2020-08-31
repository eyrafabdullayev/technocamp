package com.ecommerce.dao.inter;

import com.ecommerce.entity.Store;

import java.util.List;

public interface StoreDaoInter {

    List<Store> getAllStories();

    Store getStoreById(Integer id);

    Store getStoreByUserId(Integer id);

    boolean updateStore(Store s);

    boolean insertStore(Store s);

    boolean deleteStore(Integer id);
}
