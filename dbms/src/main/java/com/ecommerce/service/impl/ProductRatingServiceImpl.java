package com.ecommerce.service.impl;

import com.ecommerce.dao.inter.ProductRatingDaoInter;
import com.ecommerce.entity.ProductRating;
import com.ecommerce.service.inter.ProductRatingServiceInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductRatingServiceImpl implements ProductRatingServiceInter {

    @Autowired
    @Qualifier("productRatingDao")
    ProductRatingDaoInter productRatingDao;

    @Override
    public boolean insert(ProductRating pr) {
        return productRatingDao.insert(pr);
    }
}
