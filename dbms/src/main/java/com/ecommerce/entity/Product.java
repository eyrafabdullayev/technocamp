package com.ecommerce.entity;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name = "product")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "product_id")
    private Integer productId;
    @JoinColumn(name = "user_id")
    @ManyToOne(optional = false)
    private User userId;
    @JoinColumn(name = "store_id")
    @ManyToOne(optional = false)
    private Store store;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @Column(name = "price")
    private double price;
    @Basic(optional = false)
    @Column(name = "currency")
    private String currency;
    @Basic(optional = false)
    @Column(name = "category_id")
    private int categoryId;
    @Basic(optional = false)
    @Column(name = "subcategory_id")
    private int subcategoryId;
    @Column(name = "status")
    private Short status;
    @Column(name = "date")
    private String date;
    @Column(name = "discount")
    private int discount;
    @Column(name = "type")
    private int type;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product", fetch = FetchType.EAGER)
    private Set<ProductPictures> productPicturesSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product", fetch = FetchType.EAGER)
    private Set<ProductRating> productRatings;

    public Product() {
    }

    public Product(Integer productId) {
        this.productId = productId;
    }

    public Product(Integer productId, String name, String description, double price, String currency, int categoryId, int subcategoryId) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.currency = currency;
        this.categoryId = categoryId;
        this.subcategoryId = subcategoryId;
    }

    public Product(User userId, Store store, String name, String description, double price, String currency, int categoryId, int subcategoryId) {
        this.userId = userId;
        this.store = store;
        this.name = name;
        this.description = description;
        this.price = price;
        this.currency = currency;
        this.categoryId = categoryId;
        this.subcategoryId = subcategoryId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public User getUser() {
        return userId;
    }

    public void setUser(User user) {
        this.userId = user;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getSubcategoryId() {
        return subcategoryId;
    }

    public void setSubcategoryId(int subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Set<ProductPictures> getProductPicturesSet() {
        return productPicturesSet;
    }

    public void setProductImageList(Set<ProductPictures> productPicturesList) {
        this.productPicturesSet = productPicturesList;
    }

    public Set<ProductRating> getProductRatings() {
        return productRatings;
    }

    public void setProductRatings(Set<ProductRating> productRatings) {
        this.productRatings = productRatings;
    }

    @Override
    public String toString() {
        return "com.ecommerce.entity.Product[ productId=" + productId + " ]";
    }
    
}
