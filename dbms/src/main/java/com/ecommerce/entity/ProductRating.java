package com.ecommerce.entity;

import javax.persistence.*;

@Entity
@Table(name = "product_rating")
public class ProductRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    @JoinColumn(name = "client_name")
    @Basic(optional = false)
    private String clientName;
    @JoinColumn(name = "client_email")
    @Basic(optional = false)
    private String clientEmail;
    @JoinColumn(name = "rating")
    @Basic(optional = false)
    private Integer rating;
    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private Product product;
    @JoinColumn(name = "date")
    @Basic(optional = false)
    private String date;

    public ProductRating() {
    }

    public ProductRating(String clientName, String clientEmail, Integer rating, Product product) {
        this.clientName = clientName;
        this.clientEmail = clientEmail;
        this.rating = rating;
        this.product = product;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
