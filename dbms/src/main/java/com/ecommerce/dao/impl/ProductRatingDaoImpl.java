package com.ecommerce.dao.impl;

import com.ecommerce.dao.inter.ProductRatingDaoInter;
import com.ecommerce.entity.ProductRating;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository("productRatingDao")
public class ProductRatingDaoImpl implements ProductRatingDaoInter {

    @PersistenceContext
    EntityManager em;

    @Override
    public boolean insert(ProductRating pr) {
        boolean result = false;
        try {
            em.createNativeQuery("INSERT INTO product_rating (client_name,client_email,rating,product_id) VALUES(?,?,?,?)")
                    .setParameter(1, pr.getClientName())
                    .setParameter(2, pr.getClientEmail())
                    .setParameter(3, pr.getRating())
                    .setParameter(4, pr.getProduct().getProductId())
                    .executeUpdate();
            result = true;
        } catch (Exception e) {

        } finally {
            return result;
        }
    }
}
