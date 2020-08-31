package com.ecommerce.controller.cabinet;

import com.ecommerce.config.ApplicationConfig;
import com.ecommerce.config.SignedUser;
import com.ecommerce.entity.*;
import com.ecommerce.form.UserProductForm;
import com.ecommerce.service.inter.CategoryServiceInter;
import com.ecommerce.service.inter.ProductPicturesServiceInter;
import com.ecommerce.service.inter.ProductServiceInter;
import com.ecommerce.service.inter.UserServiceInter;
import com.ecommerce.util.SubcategoriesByCategoryId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class UserProductController {

    @Autowired
    ApplicationConfig config;

    @Autowired
    UserServiceInter userService;

    @Autowired
    SubcategoriesByCategoryId subcategoriesByCategoryId;

    @Autowired
    CategoryServiceInter categoryService;

    @Autowired
    ProductServiceInter productService;

    @Autowired
    ProductPicturesServiceInter productImageService;

    @GetMapping("/cabinet/products")
    public String productsPage(Model model) {
        User signedUser;
        List<Product> productList;
        try {
            signedUser = userService.getUserById(SignedUser.signedUser.getId());

            if (signedUser != null) {
                model.addAttribute("signedUser", signedUser);

                productList = new ArrayList<>(signedUser.getProductSet());
                if (signedUser.getProductSet() != null) {
                    model.addAttribute("products", productList);
                }
            }
        } catch (Exception e) {
            throw new IllegalStateException(e.getLocalizedMessage());
        }

        return "cabinet/products";
    }

    @GetMapping("/cabinet/add-product")
    public String productAddingPage(Model model) {
        User signedUser;
        List<Category> categoryList;
        try {
            signedUser = SignedUser.signedUser;

            if (signedUser != null) {
                model.addAttribute("signedUser", signedUser);

                categoryList = categoryService.getCategoryList();
                if (categoryList != null) {
                    model.addAttribute("categoryList", categoryList);
                }
            }
        } catch (Exception e) {
            throw new IllegalStateException(e.getLocalizedMessage());
        }
        return "cabinet/add-product";
    }

    @PostMapping("/cabinet/add-product")
    @ResponseBody
    public String productAdd(@Valid @ModelAttribute UserProductForm productForm,
                             BindingResult result) {
        if (!result.hasErrors()) {

            List<MultipartFile> files = productForm.getImages();

            //if there are some images this part will be execute
            if (files != null && files.size() > 0) {

                List<String> contentTypes = new ArrayList<>();

                //max size
                int maxSize = 1024*1024*5;//5MB

                //allowed content types
                String[] allowedExtensions = {
                        "image/jpeg",
                        "image/jpg",
                        "image/png"
                };

                //controlling size and content type of images/ image
                for (MultipartFile file : files) {
                    if(file.getSize() > maxSize)
                        return "Size of image is bigger than excepted size: " + file.getOriginalFilename() + file.getContentType();
                    for(String type : allowedExtensions) {
                        if(type.equals(file.getContentType())) {
                            contentTypes.add(type);
                            break;
                        }
                    }
                }

                if (contentTypes.size() != files.size())
                    return "Type of image is not allowed, only JPEG, JPG, PNG is allowed.";

                ArrayList<String> imagesName = (ArrayList<String>) contentTypes.stream()
                        .map(type -> {
                            String uuid = UUID.randomUUID().toString();
                            String contentType = type.equals("image/jpeg") ? ".jpeg" : (type.equals("image/jpg") ? ".jpg" : ".png");
                            return "product-" + uuid + contentType;
                        })
                        .collect(Collectors.toList());


                String folder = config.getPhotos();

                try {

                    User user = SignedUser.signedUser;
                    Store store = user.getStore();
                    String name = productForm.getName();
                    String description = productForm.getDescription();
                    String currency = productForm.getCurrency();
                    Double price = productForm.getPrice();
                    Integer categoryId = productForm.getCategoryId();
                    Integer subcategoryId = productForm.getSubcategoryId();

                    //insert product
                    Product product = new Product(user, store, name, description, price, currency, categoryId, subcategoryId);
                    productService.insert(product);

                    Integer lastItemIndex = productService.getLastItemIndex();

                    int i = 0;
                    while (i < files.size()) {

                        Path path = Paths.get(folder + imagesName.get(i));
                        try {
                            Files.write(path, files.get(i).getBytes());
                        } catch (IOException e) {
                            return e.getLocalizedMessage();
                        }

                        //insert its image
                        productImageService.insert(lastItemIndex, imagesName.get(i++));
                    }
                } catch (Exception e) {
                    return "Internal Server Error";
                }
            } else {
                return "You have to add an image for product!";
            }
        } else {
            return "Please type all required fields!";
        }
        return "Operation was successfully! Your request will be accepted about few hours by moderator.";
    }

    @GetMapping("/cabinet/update-product/{productId}")
    public String productUpdatePage(Model model,
                                    @PathVariable("productId") Integer productId) throws IllegalStateException {
        User signedUser;
        Product product = null;
        List<Category> categoryList;
        List<ProductPictures> productImageList = new ArrayList<>();
        try {
            signedUser = userService.getUserById(SignedUser.signedUser.getId());

            if (signedUser != null) {
                model.addAttribute("signedUser", signedUser);

                //get all categories
                categoryList = categoryService.getCategoryList();
                if (categoryList != null) {
                    model.addAttribute("categoryList", categoryList);
                }

                //get user's product by productId
                Set<Product> products =signedUser.getProductSet();
                for (Product p : products) {
                    if (p.getProductId().equals(productId)) {
                        product = p;
                        break;
                    }
                }

                if (product != null) {
                    model.addAttribute("product", product);

                    //get images of product
                    productImageList.addAll(product.getProductPicturesSet());

                    if (productImageList.size() > 0) {
                        model.addAttribute("productImageList", productImageList);
                    }
                }
            }
        } catch (Exception e) {
            throw new IllegalStateException(e.getLocalizedMessage());
        }
        return "cabinet/update-product";
    }

    @PostMapping("/cabinet/update-product/{productId}")
    @ResponseBody
    public String productUpdate(@Valid @ModelAttribute UserProductForm productForm, BindingResult result,
                                @PathVariable("productId") Integer productId) {
        Product product;
        List<ProductPictures> productImageList;
        List<MultipartFile> fileList = productForm.getImages();

        if(!result.hasErrors()) {

            product = productService.getProductById(productId);

            //if there are some images this part will be execute
            if(fileList != null) {
                if (!fileList.get(0).isEmpty()) {

                    productImageList = new ArrayList<>(product.getProductPicturesSet());

                    //can user add new image for current product or not ?
                    if(productImageList.size() + fileList.size() > 3) {
                        return "You cannot add another new image for this product, because you had 3 chance!";
                    }

                    List<String> contentTypes = new ArrayList<>();

                    //max size
                    int maxSize = 1024*1024*5;//5MB

                    //allowed content types
                    String[] allowedExtensions = {
                            "image/jpeg",
                            "image/jpg",
                            "image/png"
                    };

                    //controlling size and content type of images/ image
                    for (MultipartFile file : fileList) {
                        if(file.getSize() > maxSize)
                            return "Size of image is bigger than excepted size: " + file.getOriginalFilename() + file.getContentType();
                        for(String type : allowedExtensions) {
                            if(type.equals(file.getContentType())) {
                                contentTypes.add(type);
                                break;
                            }
                        }
                    }

                    if (contentTypes.size() != fileList.size())
                        return "Type of image is not allowed, only JPEG, JPG, PNG is allowed.";

                    List<String> newImages = contentTypes.stream()
                            .map(type -> {
                                String uuid = UUID.randomUUID().toString();
                                String contentType = type.equals("image/jpeg") ? ".jpeg" : (type.equals("image/jpg") ? ".jpg" : ".png");
                                return "product-" + uuid + contentType;
                            })
                            .collect(Collectors.toList());

                    String folder = config.getPhotos();

                    //adding new images to the database
                    int i=0;
                    while (i < fileList.size()) {
                        MultipartFile file = fileList.get(i);

                        try {
                            Path path = Paths.get(folder + newImages.get(i));
                            Files.write(path,file.getBytes());

                            productImageService.insert(productId,newImages.get(i++));
                        } catch (IOException e) {
                            return "Internal Server Error!";
                        }
                    }
                }
            }

            //doesn't matter any image was added or not, this part will be execute.
            String name = productForm.getName();
            String description = productForm.getDescription();
            String currency = productForm.getCurrency();
            Double price = productForm.getPrice();
            Integer categoryId = productForm.getCategoryId();
            Integer subcategoryId = productForm.getSubcategoryId();

            product.setName(name);
            product.setDescription(description);
            product.setCurrency(currency);
            product.setPrice(price);
            product.setCategoryId(categoryId);
            product.setSubcategoryId(subcategoryId);

            //update product
            try {
                productService.update(product);
            } catch (Exception e) {
                return "Internal Server Error!";
            }

        } else {
            return "Please add all fields with its required type!";
        }

        return "Operation was successfully! Your request will be accepted about few hours by moderator.";
    }

    @GetMapping("/cabinet/delete-product-image/{productImageId}")
    @ResponseBody
    public String productImageDelete(@PathVariable("productImageId") Integer imageId) {
        try {
            String imageURL;
            if(imageId != null) {
                ProductPictures productImage = productImageService.getImageById(imageId);
                productImageService.delete(productImage);

                new File(config.getPhotos() + productImage.getImageURL()).delete();
            } else {
                return "Image not found!";
            }
        } catch (Exception e) {
            return "Internal Server Error";
        }
        return "Operation was successfully!";
    }

    @GetMapping("/cabinet/product/{categoryId}/{subcategoryId}")
    @ResponseBody
    public String getSubcategories(@PathVariable("categoryId") Integer categoryId, @PathVariable("subcategoryId") Integer subcategoryId) {
        return subcategoriesByCategoryId.getSubcategories(categoryId,subcategoryId);
    }
}
