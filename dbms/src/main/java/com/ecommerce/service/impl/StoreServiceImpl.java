package com.ecommerce.service.impl;

import com.ecommerce.dao.inter.StoreDaoInter;
import com.ecommerce.entity.Store;
import com.ecommerce.service.inter.StoreServiceInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StoreServiceImpl implements StoreServiceInter {

    @Autowired
    @Qualifier("storeDao")
    StoreDaoInter storeDao;


    @Override
    public List<Store> getAllStories() {
        return storeDao.getAllStories();
    }

    @Override
    public Store getStoreById(Integer id) {
        return storeDao.getStoreById(id);
    }

    @Override
    public Store getStoreByUserId(Integer id) {
        return storeDao.getStoreByUserId(id);
    }

    @Override
    public boolean updateStore(Store s) {
        return storeDao.updateStore(s);
    }

    @Override
    public boolean insertStore(Store s) {
        return storeDao.insertStore(s);
    }

    @Override
    public boolean deleteStore(Integer id) {
        return storeDao.deleteStore(id);
    }
}
