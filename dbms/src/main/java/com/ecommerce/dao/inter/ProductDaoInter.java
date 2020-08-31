package com.ecommerce.dao.inter;

import com.ecommerce.entity.Product;

import java.text.ParseException;
import java.util.List;

public interface ProductDaoInter {

    List<Product> getAllProducts();

    Product getProductById(Integer id);

    Product getProductByStoreId(Integer storeId);

    Product getProductByUserId(Integer userId);

    List<Product> getProductsByCategoryIdAndProductName(Integer id, String name);

    Integer getLastItemIndex();

    List<Product> getWeeklyProducts() throws ParseException;

    boolean update(Product p);

    boolean insert(Product p);

    boolean delete(Product p);
}
