package com.ecommerce.util;

import com.ecommerce.entity.Subcategory;
import com.ecommerce.service.inter.SubcategoryServiceInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SubcategoriesByCategoryId {

    @Autowired
    SubcategoryServiceInter subcategoryService;

    public String getSubcategories(Integer id, Integer subcategoryId) {
        String html = "";

        List<Subcategory> subcategoriesByCategoryId = subcategoryService.getSubcategoriesByCategoryId(id);
        for (Subcategory subcategory : subcategoriesByCategoryId) {
            html += "<option value='" + subcategory.getId() + "'";
            html += (subcategoryId == 0) ? ">" : (subcategoryId.equals(subcategory.getId()))
                    ? " selected='"+"selected"+"' >" : ">";
            html += subcategory.getName() + "</option>";
        }

        return html;
    }
}
