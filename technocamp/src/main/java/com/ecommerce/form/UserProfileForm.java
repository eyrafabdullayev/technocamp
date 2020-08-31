package com.ecommerce.form;

import javax.validation.constraints.NotNull;

public class UserProfileForm {

    @NotNull
    private Integer id;
    @NotNull
    private String name;
    @NotNull
    private String surname;
    @NotNull
    private String username;
    @NotNull
    private String phone;
    @NotNull
    private String city;
    @NotNull
    private Integer country;

    public UserProfileForm(@NotNull Integer id, @NotNull String name, @NotNull String surname, @NotNull String username, @NotNull String phone, @NotNull String city, @NotNull Integer country) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.phone = phone;
        this.city = city;
        this.country = country;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getUsername() {
        return username;
    }

    public String getPhone() {
        return phone;
    }

    public String getCity() {
        return city;
    }

    public Integer getCountry() {
        return country;
    }
}
