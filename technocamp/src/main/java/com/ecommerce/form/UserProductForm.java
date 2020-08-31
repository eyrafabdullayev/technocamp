package com.ecommerce.form;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class UserProductForm {
    private String name;
    private Double price;
    private String currency;
    private Integer categoryId;
    private Integer subcategoryId;
    private String description;
    private List<MultipartFile> images;

    public UserProductForm(String name, Double price, String currency, Integer categoryId, Integer subcategoryId, String description, List<MultipartFile> images) {
        this.name = name;
        this.price = price;
        this.currency = currency;
        this.categoryId = categoryId;
        this.subcategoryId = subcategoryId;
        this.description = description;
        this.images = images;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getSubcategoryId() {
        return subcategoryId;
    }

    public void setSubcategoryId(Integer subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<MultipartFile> getImages() { return images; }

    public void setImages(List<MultipartFile> images) { this.images = images; }
}
