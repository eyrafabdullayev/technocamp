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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
public class WishlistController {

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

    @GetMapping(value = "/wishlist")
    public String wishlistPage(@CookieValue(name = "product", defaultValue = "") String productCookie,
                               Model model) {
        List<Product> productList = new ArrayList<>();
        if (!productCookie.isEmpty()) {

            //is there any signed user
            if (SignedUser.signedUser != null) {
                model.addAttribute("signedUser", SignedUser.signedUser);
            }

            String[] ids = productCookie.split("C");
            for (String id : ids) {
                Product product = productService.getProductById(Integer.valueOf(id));
                productList.add(product);
            }
            if (productList.size() > 0) {
                model.addAttribute("productList", productList);

                //categoryList
                model.addAttribute("categoryList",categoryService.getCategoryList());

                //subcategoryList
                model.addAttribute("subcategoryList",subcategoryService.getSubcategoryList());
            }

            //site details
            model.addAttribute("details",detailsRepository.getDetails());
        }
        return "wishlist";
    }

    @PostMapping(value = "/add-to-wishlist/{productId}")
    @ResponseBody
    public String sendRating(@CookieValue(name = "product", defaultValue = "") String productCookies,
                             @PathVariable("productId") Integer productId, HttpServletResponse response) {
        if (productId != null) {
            //read all cookies which name is product
            String cookies = "";
            if (!productCookies.isEmpty()) {
                cookies = productCookies;
            }

            //create a cookie
            String newValue = !cookies.isEmpty() ? cookies + "C" + productId : productId + "";

            Cookie cookie = new Cookie("product", newValue);
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            cookie.setMaxAge(7 * 24 * 60 * 60);// 1 week
            cookie.setPath("/");

            //add cookie to response
            response.addCookie(cookie);
            return "Request was successfully!";
        }
        return "Bad request!";
    }

    @GetMapping(value = "/wishlist-size")
    @ResponseBody
    public String getWishlistSize(@CookieValue(name = "product", defaultValue = "") String productCookies) {
        if (!productCookies.isEmpty()) {
            String[] ids = productCookies.split("C");
            return ids.length + "";
        }
        return "0";
    }
}
