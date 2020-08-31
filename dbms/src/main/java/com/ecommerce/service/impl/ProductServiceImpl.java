package com.ecommerce.service.impl;

import com.ecommerce.dao.inter.ProductDaoInter;
import com.ecommerce.entity.Product;
import com.ecommerce.service.inter.ProductServiceInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductServiceInter {

    @Autowired
    @Qualifier("productDao")
    ProductDaoInter productDao;


    @Override
    public List<Product> getAllProducts() {
        return productDao.getAllProducts();
    }

    @Override
    public Product getProductById(Integer id) {
        return productDao.getProductById(id);
    }

    @Override
    public Product getProductByStoreId(Integer storeId) {
        return productDao.getProductByStoreId(storeId);
    }

    @Override
    public Product getProductByUserId(Integer userId) {
        return productDao.getProductByUserId(userId);
    }

    @Override
    public List<Product> getProductsByCategoryIdAndProductName(Integer id, String name) {
        return productDao.getProductsByCategoryIdAndProductName(id, name);
    }

    @Override
    public Integer getLastItemIndex() {
        return productDao.getLastItemIndex();
    }

    @Override
    public List<Product> getWeeklyProducts() throws ParseException {
        return productDao.getWeeklyProducts();
    }

    @Override
    public boolean update(Product p) {
        return productDao.update(p);
    }

    @Override
    public boolean insert(Product p) {
        return productDao.insert(p);
    }

    @Override
    public boolean delete(Product p) {
        return productDao.delete(p);
    }
}
