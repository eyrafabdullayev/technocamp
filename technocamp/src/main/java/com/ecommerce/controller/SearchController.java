package com.ecommerce.controller;

import com.ecommerce.config.SignedUser;
import com.ecommerce.dao.inter.DetailsRepository;
import com.ecommerce.entity.Product;
import com.ecommerce.service.inter.CategoryServiceInter;
import com.ecommerce.service.inter.ProductServiceInter;
import com.ecommerce.service.inter.SubcategoryServiceInter;
import com.ecommerce.service.inter.UserServiceInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class SearchController {

    @Autowired
    UserServiceInter userService;

    @Autowired
    ProductServiceInter productService;

    @Autowired
    CategoryServiceInter categoryService;

    @Autowired
    SubcategoryServiceInter subcategoryService;

    @Autowired
    DetailsRepository detailsRepository;

    @GetMapping(value = "/search/{categoryId}/{productName}")
    public String searchPage(@PathVariable("categoryId") Integer categoryId,
                             @PathVariable("productName") String productName,
                             @CookieValue(name = "product", defaultValue = "") String productCookie,
                             Model model) {
        //site details
        model.addAttribute("details",detailsRepository.getDetails());

        //Search by category id
        List<Product> productList = productService.getProductsByCategoryIdAndProductName(categoryId,productName);
        if (productList != null) {

            //is there any signed user
            if (SignedUser.signedUser != null) {
                model.addAttribute("signedUser", SignedUser.signedUser);
            }

            model.addAttribute("productList", productList);

            //get all categories
            model.addAttribute("categoryList",categoryService.getCategoryList());

            //get all subcategories
            model.addAttribute("subcategoryList",subcategoryService.getSubcategoryList());

            //wishlist
            Map<Integer, Integer> wishlist = new HashMap<>();

            String[] ids = productCookie.split("C");
            for (String id : ids) {
                Integer productId = Integer.valueOf(id);
                wishlist.put(productId, 1);
            }
            model.addAttribute("wishlist",wishlist);
        }

        return "search";
    }
}
