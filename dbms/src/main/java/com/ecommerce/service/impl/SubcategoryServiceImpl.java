package com.ecommerce.service.impl;

import com.ecommerce.dao.inter.SubcategoryDaoInter;
import com.ecommerce.entity.Subcategory;
import com.ecommerce.service.inter.SubcategoryServiceInter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SubcategoryServiceImpl implements SubcategoryServiceInter{
    
    @Autowired
    @Qualifier("subcategoryDao")
    SubcategoryDaoInter subcategoryDao;

    @Override
    public List<Subcategory> getSubcategoryList() {
        return subcategoryDao.getSubcategoryList();
    }

    @Override
    public Subcategory getSubcategoryById(Integer id) {
        return subcategoryDao.getSubcategoryById(id);
    }

    @Override
    public List<Subcategory> getSubcategoriesByCategoryId(Integer id) {
        return subcategoryDao.getSubcategoriesByCategoryId(id);
    }

    @Override
    public boolean updateSubcategory(Subcategory s) {
        return subcategoryDao.updateSubcategory(s);
    }

    @Override
    public boolean insertSubcategory(Subcategory s) {
        return subcategoryDao.insertSubcategory(s);
    }

    @Override
    public boolean deleteSubcategory(Integer id) {
        return subcategoryDao.deleteSubcategory(id);
    }
}
