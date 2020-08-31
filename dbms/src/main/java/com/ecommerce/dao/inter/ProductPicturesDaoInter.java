package com.ecommerce.dao.inter;

import com.ecommerce.entity.Product;
import com.ecommerce.entity.ProductPictures;

import java.util.List;

public interface ProductPicturesDaoInter {

    List<ProductPictures> getAllProductImages();

    ProductPictures getImageById(Integer id);

    ProductPictures getProductImage(Product p);

    boolean update(ProductPictures productPictures);

    boolean insert(Integer lastItemIndex, String imageURL);

    boolean delete(ProductPictures productPictures);
}
