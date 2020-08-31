package com.ecommerce.service.impl;

import com.ecommerce.dao.inter.CategoryDaoInter;
import com.ecommerce.entity.Category;
import com.ecommerce.service.inter.CategoryServiceInter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryServiceInter {

    @Autowired
    @Qualifier("categoryDao")
    CategoryDaoInter categoryDao;
    
    @Override
    public List<Category> getCategoryList() {
        return categoryDao.getCategoryList();
    }

    @Override
    public Category getCategoryById(Integer id) {
        return categoryDao.getCategoryById(id);
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryDao.getCategoryByName(name);
    }

    @Override
    public boolean updateCategory(Category c) {
        return categoryDao.updateCategory(c);
    }

    @Override
    public boolean insertCategory(Category c) {
        return categoryDao.insertCategory(c);
    }

    @Override
    public boolean deleteCategory(Integer id) {
        return categoryDao.deleteCategory(id);
    }
    
}
