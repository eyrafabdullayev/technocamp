package com.ecommerce.dao.inter;

import com.ecommerce.entity.Subcategory;
import java.util.List;

public interface SubcategoryDaoInter {
    
    List<Subcategory> getSubcategoryList();   
    
    Subcategory getSubcategoryById(Integer id);
    
    List<Subcategory> getSubcategoriesByCategoryId(Integer id);
    
    boolean updateSubcategory(Subcategory s);
    
    boolean insertSubcategory(Subcategory s);
    
    boolean deleteSubcategory(Integer id);
}
