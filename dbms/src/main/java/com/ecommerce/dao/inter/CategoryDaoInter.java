package com.ecommerce.dao.inter;

import com.ecommerce.entity.Category;
import java.util.List;

public interface CategoryDaoInter {
    
    List<Category> getCategoryList();   
    
    Category getCategoryById(Integer id);

    Category getCategoryByName(String name);
    
    boolean updateCategory(Category c);
    
    boolean insertCategory(Category c);
    
    boolean deleteCategory(Integer id);
}
