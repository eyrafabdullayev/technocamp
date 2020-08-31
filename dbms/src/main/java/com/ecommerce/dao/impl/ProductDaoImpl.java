package com.ecommerce.dao.impl;

import com.ecommerce.dao.inter.ProductDaoInter;
import com.ecommerce.entity.Product;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository("productDao")
public class ProductDaoImpl implements ProductDaoInter {

    @PersistenceContext
    EntityManager em;

    @Override
    public List<Product> getAllProducts() {
        return (List<Product>) em.createQuery("SELECT p FROM Product p")
                .getResultList();
    }

    @Override
    public Product getProductById(Integer id) {
        return (Product) em.createQuery("SELECT p FROM Product p WHERE p.productId = :id")
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public Product getProductByStoreId(Integer storeId) {
        return (Product) em.createQuery("SELECT p FROM Product p WHERE p.store.id = :id")
                .setParameter("id", storeId)
                .getSingleResult();
    }

    @Override
    public Product getProductByUserId(Integer userId) {
        return (Product) em.createQuery("SELECT p FROM Product p WHERE p.userId = :id")
                .setParameter("id", userId)
                .getSingleResult();
    }

    @Override
    public List<Product> getProductsByCategoryIdAndProductName(Integer id, String name) {
        return (List<Product>) em.createQuery("SELECT p FROM Product p WHERE p.categoryId = :categoryId and p.name LIKE :name")
                .setParameter("categoryId", id)
                .setParameter("name", "%" + name + "%")
                .getResultList();
    }

    @Override
    public Integer getLastItemIndex() {
        ArrayList<Product> allProducts = (ArrayList<Product>) getAllProducts();
        return allProducts.get(allProducts.size() - 1).getProductId();
    }

    @Override
    public List<Product> getWeeklyProducts() {
        List<Product> productList = getAllProducts();

        List<Product> weeklyProducts = new ArrayList<>();
        for (Product p : productList) {
            if (p.getType() == 1) {
                weeklyProducts.add(p);
            }
        }

        return weeklyProducts;
    }


    @Override
    public boolean update(Product p) {
        boolean result = false;
        try {
            em.createQuery("UPDATE Product p SET p.name = :name, p.price = :price, p.categoryId = :categoryId, p.subcategoryId = :subcategoryId, p.currency = :currency, p.description = :description, p.status = :status, p.type = :type WHERE p.productId = :productId")
                    .setParameter("name", p.getName())
                    .setParameter("price", p.getPrice())
                    .setParameter("categoryId", p.getCategoryId())
                    .setParameter("subcategoryId", p.getSubcategoryId())
                    .setParameter("currency", p.getCurrency())
                    .setParameter("description", p.getDescription())
                    .setParameter("status", p.getStatus())
                    .setParameter("type", p.getType())
                    .setParameter("productId", p.getProductId())
                    .executeUpdate();
            result = true;
        } catch (Exception e) {

        } finally {
            return result;
        }
    }

    @Override
    public boolean insert(Product p) {
        boolean result = false;
        try {
            em.createNativeQuery("INSERT INTO product (user_id,store_id,name,description,price,currency,category_id,subcategory_id) VALUES(?,?,?,?,?,?,?,?)")
                    .setParameter(1, p.getUser().getId())
                    .setParameter(2, p.getStore().getId())
                    .setParameter(3, p.getName())
                    .setParameter(4, p.getDescription())
                    .setParameter(5, p.getPrice())
                    .setParameter(6, p.getCurrency())
                    .setParameter(7, p.getCategoryId())
                    .setParameter(8, p.getSubcategoryId())
                    .executeUpdate();
            result = true;
        } catch (Exception e) {

        } finally {
            return result;
        }
    }

    @Override
    public boolean delete(Product p) {
        boolean result = false;
        try {
            em.createQuery("DELETE FROM Product p WHERE p.productId = :id")
                    .setParameter("id", p.getProductId())
                    .executeUpdate();
            result = true;
        } catch (Exception e) {

        } finally {
            return result;
        }
    }
}
