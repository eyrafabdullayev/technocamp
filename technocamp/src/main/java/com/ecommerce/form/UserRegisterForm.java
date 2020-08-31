package com.ecommerce.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserRegisterForm {

    @NotNull
    @Size(min = 3, max = 45)
    private String username;
    @NotNull
    @Size(min = 3, max = 45)
    private String name;
    @NotNull
    @Size(min = 3, max = 45)
    private String surname;
    @NotNull
    @Size(min = 10)
    private String phone;
    @NotNull
    private Integer countryId;
    @NotNull
    @Size(min = 3)
    private String city;
    @NotNull
    private String type;
    @NotNull
    @Size(min = 6)
    private String password;
    @NotNull
    @Size(min = 6)
    private String passwordVerify;

    public UserRegisterForm(@NotNull String username, @NotNull String name, @NotNull String surname, @NotNull String phone, @NotNull Integer countryId, @NotNull String city, @NotNull String type, @NotNull String password, @NotNull String passwordVerify) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.countryId = countryId;
        this.city = city;
        this.type = type;
        this.password = password;
        this.passwordVerify = passwordVerify;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordVerify() {
        return passwordVerify;
    }

    public void setPasswordVerify(String passwordVerify) {
        this.passwordVerify = passwordVerify;
    }
}
