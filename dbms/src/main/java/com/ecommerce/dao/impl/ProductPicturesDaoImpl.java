package com.ecommerce.dao.impl;

import com.ecommerce.dao.inter.ProductPicturesDaoInter;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.ProductPictures;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository("productImageDao")
public class ProductPicturesDaoImpl implements ProductPicturesDaoInter {

    @PersistenceContext
    EntityManager em;

    @Override
    public List<ProductPictures> getAllProductImages() {
        return (List<ProductPictures>) em.createQuery("SELECT i FROM ProductPictures i")
                .getResultList();
    }

    @Override
    public ProductPictures getImageById(Integer id) {
        return (ProductPictures) em.createQuery("SELECT i FROM ProductPictures i WHERE i.id = :id")
                .setParameter("id",id)
                .getSingleResult();
    }

    @Override
    public ProductPictures getProductImage(Product p) {
        return (ProductPictures) em.createQuery("SELECT i FROM ProductPictures i WHERE i.productId = :product")
                .setParameter("product",p)
                .getSingleResult();
    }

    @Override
    public boolean update(ProductPictures productPictures) {
        boolean result = false;
        try {
            em.createQuery("UPDATE ProductPictures i SET i.imageURL = :image WHERE i.id = :id")
                    .setParameter("image", productPictures.getImageURL())
                    .setParameter("id", productPictures.getId())
                    .executeUpdate();
            result = true;
        } catch (Exception e) {

        } finally {
            return result;
        }
    }

    @Override
    public boolean insert(Integer lastItemIndex, String imageURL) {
        boolean result = false;
        try {
            em.createNativeQuery("INSERT INTO product_image (product_id,imageURL) VALUES(?,?)")
                    .setParameter(1, lastItemIndex)
                    .setParameter(2, imageURL)
                    .executeUpdate();
            result = true;
        } catch (Exception e) {

        } finally {
            return result;
        }
    }

    @Override
    public boolean delete(ProductPictures productPictures) {
        boolean result = false;
        try {
            em.createQuery("DELETE FROM ProductPictures i WHERE i.id = :id")
                    .setParameter("id", productPictures.getId())
                    .executeUpdate();
            result = true;
        } catch (Exception e) {

        } finally {
            return result;
        }
    }
}
