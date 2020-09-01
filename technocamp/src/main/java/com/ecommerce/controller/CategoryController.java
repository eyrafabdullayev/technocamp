package com.ecommerce.controller;

import com.ecommerce.config.SignedUser;
import com.ecommerce.dao.inter.DetailsRepository;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.User;
import com.ecommerce.service.inter.CategoryServiceInter;
import com.ecommerce.service.inter.ProductServiceInter;
import com.ecommerce.service.inter.SubcategoryServiceInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class CategoryController {

    @Autowired
    ProductServiceInter productService;

    @Autowired
    CategoryServiceInter categoryService;

    @Autowired
    SubcategoryServiceInter subcategoryService;

    @Autowired
    DetailsRepository detailsRepository;

    @GetMapping(value = "/category/{categoryName}/{categoryId}")
    public String categoryPage(@CookieValue(value = "product", defaultValue = "") String productCookie,
                               @PathVariable("categoryName") String categoryName,
                               @PathVariable("categoryId") Integer categoryId,
                               Model model) {

        //check path variables
        if (!isNumeric(categoryId)) {
            return "404";
        }

        //if there is any user
        User signedUser = SignedUser.signedUser;
        if (signedUser != null) {
            model.addAttribute("signedUser", signedUser);
        }

        if (categoryName != null) {
            model.addAttribute("categoryName", categoryName);
        }

        //site detail
        model.addAttribute("details", detailsRepository.getDetails());

        //product list
        List<Product> productList;
        if (categoryName.equals("allcategories")) {
            productList = productService.getAllProducts();
        } else {
            productList = productService.getAllProducts().stream()
                    .filter(product -> product.getCategoryId() == categoryId)
                    .collect(Collectors.toList());
        }

        model.addAttribute("productList", productList);

        //get category list
        model.addAttribute("categoryList", categoryService.getCategoryList());

        if (productList.size() > 0) {
            //get subcategory list
            model.addAttribute("subcategoryList", subcategoryService.getSubcategoryList());
        }

        //wishlist
        Map<Integer, Integer> wishlist = new HashMap<>();

        String[] ids = productCookie.split("C");
        for (String id : ids) {
            Integer productId = Integer.valueOf(id);
            wishlist.put(productId, 1);
        }

        model.addAttribute("wishlist", wishlist);

        return "category";
    }

    public boolean isNumeric(Integer id) {
        return id != null && String.valueOf(id).matches("[-+]?\\d*\\.?\\d+");
    }
}
