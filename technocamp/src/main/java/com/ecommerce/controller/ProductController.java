package com.ecommerce.controller;

import com.ecommerce.config.SignedUser;
import com.ecommerce.dao.inter.DetailsRepository;
import com.ecommerce.entity.*;
import com.ecommerce.form.ClientRatingRequest;
import com.ecommerce.service.inter.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.Valid;
import java.util.*;

@Controller
public class ProductController {

    @Autowired
    DetailsRepository detailsRepository;

    @Autowired
    CategoryServiceInter categoryService;

    @Autowired
    SubcategoryServiceInter subcategoryService;

    @Autowired
    ProductServiceInter productService;

    @Autowired
    ProductRatingServiceInter productRatingService;

    @GetMapping(value = "/product/{categoryName}/{subCategoryName}/{productId}")
    public ModelAndView productPage(@PathVariable("categoryName") String categoryName,
                                    @PathVariable("subCategoryName") String subCategoryName,
                                    @PathVariable("productId") Integer productId,
                                    @CookieValue(name = "product", defaultValue = "") String productCookie) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("product");

        Product product;

        if (categoryName != null && subCategoryName != null
                && productId != null) {

            //is there any user
            User signedUser = SignedUser.signedUser;
            if (signedUser != null) {
                modelAndView.addObject("signedUser", signedUser);
            }

            //get current product and its categoryName and subcategoryName
            product = productService.getProductById(productId);

            if (product != null) {
                String firstLatterOfCategoryName = "" + categoryName.charAt(0);
                firstLatterOfCategoryName.toUpperCase();

                categoryName.replace(categoryName.charAt(0), firstLatterOfCategoryName.charAt(0));

                String firstLatterOfSubCategoryName = "" + subCategoryName.charAt(0);
                firstLatterOfSubCategoryName.toUpperCase();

                subCategoryName.replace(categoryName.charAt(0), firstLatterOfSubCategoryName.charAt(0));

                modelAndView.addObject("product", product);
                modelAndView.addObject("categoryName", categoryName);
                modelAndView.addObject("subCategoryName", subCategoryName);

                //calculate ratings for each product
                Map<Integer, Long> productRatings = new HashMap<>();
                Set<ProductRating> ratings = product.getProductRatings();

                int score = 0;
                int count = 0;
                for (ProductRating rating : ratings) {
                    score += rating.getRating();
                    count++;
                }

                long average = Math.round((double) score / count);

                productRatings.put(product.getProductId(), average);

                if (productRatings != null) {
                    modelAndView.addObject("productRatings", productRatings);
                }
            }

            //get related products by category id
            List<Product> relatedProducts = productService.getAllProducts();
            relatedProducts.stream()
                    .forEach(p -> {
                        if (p.getCategoryId() != categoryService.getCategoryByName(categoryName).getId()) {
                            relatedProducts.remove(p);
                        }
                    });

            if (relatedProducts != null) {
                modelAndView.addObject("relatedProducts", relatedProducts);
            }

            //site details
            Details details = detailsRepository.getDetails();
            if (details != null) {
                modelAndView.addObject("details", details);
            }

            //get all categories
            List<Category> categoryList = categoryService.getCategoryList();
            if (categoryList != null) {
                modelAndView.addObject("categoryList", categoryList);
            }

            //get all subcategories
            List<Subcategory> subcategoryList = subcategoryService.getSubcategoryList();
            if (subcategoryList != null) {
                modelAndView.addObject("subcategoryList", subcategoryList);
            }

            //wishlist
            Map<Integer, Integer> wishlist = new HashMap<>();

            String[] ids = productCookie.split("C");
            for (String id : ids) {
                Integer pId = Integer.valueOf(id);
                wishlist.put(pId, 1);
            }
            modelAndView.addObject("wishlist", wishlist);
        }

        return modelAndView;
    }

    @PostMapping(value = "/send-rating", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map sendRating(@Valid @RequestBody ClientRatingRequest clientRatingRequest, BindingResult result) {
        if (!result.hasErrors()) {
            ProductRating pr = new ProductRating(clientRatingRequest.getName(), clientRatingRequest.getEmail(), clientRatingRequest.getRating(), new Product(clientRatingRequest.getProductId()));
            try {
                productRatingService.insert(pr);
                return Collections.singletonMap("success", "Your request was successfully!");
            } catch (Exception e) {
                return Collections.singletonMap("error", e.getLocalizedMessage());
            }
        }
        return Collections.singletonMap("error", "Bad request!");
    }
}
