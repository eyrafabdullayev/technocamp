package com.ecommerce.form;

import javax.validation.constraints.NotNull;

public class ClientRatingRequest {
    @NotNull
    private String name;
    @NotNull
    private String email;
    @NotNull
    private Integer rating;
    @NotNull
    private Integer productId;

    public ClientRatingRequest(@NotNull String name, @NotNull String email, @NotNull Integer rating, @NotNull Integer productId) {
        this.name = name;
        this.email = email;
        this.rating = rating;
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
}
