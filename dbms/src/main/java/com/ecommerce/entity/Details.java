package com.ecommerce.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "details")
public class Details {

    @Id
    @JoinColumn(name = "name")
    private String name;
    @JoinColumn(name = "phone")
    private String phone;
    @JoinColumn(name = "email")
    private String email;
    @JoinColumn(name = "address")
    private String address;
    @JoinColumn(name = "description")
    private String description;
    @JoinColumn(name = "keyword")
    private String keyword;

    public Details() {
    }

    public Details(String name, String phone, String email, String address, String description, String keyword) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.description = description;
        this.keyword = keyword;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
