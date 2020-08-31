package com.ecommerce.service.impl;

import com.ecommerce.dao.inter.ProductPicturesDaoInter;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.ProductPictures;
import com.ecommerce.service.inter.ProductPicturesServiceInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductPicturesServiceImpl implements ProductPicturesServiceInter {

    @Autowired
    @Qualifier("productImageDao")
    ProductPicturesDaoInter productImageDao;


    @Override
    public List<ProductPictures> getAllProductImages() {
        return productImageDao.getAllProductImages();
    }

    @Override
    public ProductPictures getImageById(Integer id) {
        return productImageDao.getImageById(id);
    }

    @Override
    public ProductPictures getProductImage(Product p) {
        return productImageDao.getProductImage(p);
    }

    @Override
    public boolean update(ProductPictures productPictures) {
        return productImageDao.update(productPictures);
    }

    @Override
    public boolean insert(Integer lastItemIndex, String imageURL) {
        return productImageDao.insert(lastItemIndex, imageURL);
    }

    @Override
    public boolean delete(ProductPictures productPictures) {
        return productImageDao.delete(productPictures);
    }
}
