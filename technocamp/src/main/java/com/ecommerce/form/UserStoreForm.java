package com.ecommerce.form;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

public class UserStoreForm {

    @NotNull
    private Integer userId;
    @NotNull
    private Integer storeId;
    @NotNull
    private String name;
    @NotNull
    private String phone;
    @NotNull
    private String location;
    @NotNull
    private String description;

    private MultipartFile image;

    public UserStoreForm(@NotNull Integer userId, @NotNull Integer storeId, @NotNull String name, @NotNull String phone, @NotNull String location, @NotNull String description, MultipartFile image) {
        this.userId = userId;
        this.storeId = storeId;
        this.name = name;
        this.phone = phone;
        this.location = location;
        this.description = description;
        this.image = image;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
