package com.ecommerce.controller;

import com.ecommerce.config.SignedUser;
import com.ecommerce.dao.inter.DetailsRepository;
import com.ecommerce.dao.inter.ProductSellingRepository;
import com.ecommerce.entity.*;
import com.ecommerce.form.UserRegisterForm;
import com.ecommerce.service.inter.*;
import com.ecommerce.util.TimeAgo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class TemplateController {

    @Autowired
    DetailsRepository detailsRepository;

    @Autowired
    CountryServiceInter countryService;

    @Autowired
    CategoryServiceInter categoryService;

    @Autowired
    SubcategoryServiceInter subcategoryService;

    @Autowired
    ProductServiceInter productService;

    @Autowired
    ProductSellingRepository productSellingRepository;

    @Autowired
    ProductRatingServiceInter productRatingService;

    @Autowired
    UserServiceInter userService;

    @GetMapping(value = {"", "/index"})
    public String context(@CookieValue(name = "product", defaultValue = "") String productCookie,
                          Model model) throws ParseException {

        //get site details
        Details details = detailsRepository.getDetails();
        if (details != null) {
            model.addAttribute("details", details);
        }

        //is there any signed user
        if (SignedUser.signedUser != null) {
            model.addAttribute("signedUser", SignedUser.signedUser);
        }

        //get all categories
        List<Category> categoryList = categoryService.getCategoryList();
        if (categoryList != null) {
            model.addAttribute("categoryList", categoryList);
        }

        //get all subcategories
        List<Subcategory> subcategoryList = subcategoryService.getSubcategoryList();
        if(subcategoryList != null) {
            model.addAttribute("subcategoryList",subcategoryList);
        }

        //get all products
        List<Product> productList = productService.getAllProducts();
        if (productList != null) {
            //if there are some products which was added 1 week ago, change its 'type' from 1 to 0
            productList.stream()
                    .forEach(product -> {
                        try {
                            TimeAgo timeAgo = new TimeAgo();
                            String[] split = product.getDate().split(" ");
                            int days = timeAgo.howManyDaysPassed(split[0]);

                            if (days > 7) {
                                product.setType(0);
                                productService.update(product);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
            model.addAttribute("productList", productList);

            //check new Products
            List<Product> weeklyProducts = productService.getWeeklyProducts();
            if (weeklyProducts != null) {
                model.addAttribute("weeklyProducts", weeklyProducts);
            }

            //check top selling
            List<ProductSelling> topSelling = productSellingRepository.topSellingProducts();
            if (topSelling != null) {
                List<Product> topSellingProducts = topSelling.stream()
                        .map(product -> productService.getProductById(product.getProductId()))
                        .collect(Collectors.toList());
                model.addAttribute("topSellingProducts", topSellingProducts);
            }

            //wishlist
            Map<Integer, Integer> wishlist = new HashMap<>();

            String[] ids = productCookie.split("C");
            for (String id : ids) {
                Integer productId = Integer.valueOf(id);
                wishlist.put(productId, 1);
            }
            model.addAttribute("wishlist", wishlist);
        }

        return "index";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        List<Country> countryList = countryService.getAllCountry();
        if (countryList != null) {
            model.addAttribute("countryList", countryList);
        }
        return "register";
    }

    @PostMapping("/register")
    @ResponseBody
    public String register(@Valid @ModelAttribute UserRegisterForm registerForm, BindingResult result) {
        if (!result.hasErrors()) {

            String password = registerForm.getPassword();
            String passwordVerify = registerForm.getPasswordVerify();

            //If password is equal or is't
            if (!password.equals(passwordVerify)) {
                return "Password must be equal!";
            }

            int lengthOfPassword = password.length();

            //Check length of password
            if (lengthOfPassword < 6) {
                return "Length of password don't have to be less than 6 character!";
            }

            try {
                //Check if that email is not exists in database
                User userByUsername = userService.getUserByUsername(registerForm.getUsername());
                if (userByUsername != null) {
                    return "That username already exists!";
                }

                User user = new User(null, registerForm.getName(), registerForm.getSurname(), registerForm.getUsername(), registerForm.getPhone(),
                        registerForm.getCity(), registerForm.getCountryId(), password);
                user.setType(registerForm.getType());

                userService.insertUser(user);
            } catch (Exception e) {
                return "Internal Server Error";
            }

            return "success";
        }

        return "Type all required fields!";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        SignedUser.signedUser = null;
        session.invalidate();
        return "login";
    }
}
