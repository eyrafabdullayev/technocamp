package com.ecommerce.entity;

import javax.persistence.*;

@Entity
@Table(name = "product_sell")
public class ProductSelling {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @JoinColumn(name = "id")
    private Integer id;
    @Basic(optional = false)
    @JoinColumn(name = "product_id")
    private Integer productId;
    @Basic(optional = false)
    @JoinColumn(name = "user_id")
    private Integer userId;
    @JoinColumn(name = "date")
    private String date;

    public ProductSelling() {
    }

    public ProductSelling(Integer productId, Integer userId) {
        this.productId = productId;
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ProductSelling{" +
                "id=" + id +
                ", productId=" + productId +
                ", userId=" + userId +
                ", date='" + date + '\'' +
                '}';
    }
}
