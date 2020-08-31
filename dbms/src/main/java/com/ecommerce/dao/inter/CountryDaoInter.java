package com.ecommerce.dao.inter;

import com.ecommerce.entity.Country;

import java.util.List;

public interface CountryDaoInter {

    List<Country> getAllCountry();

    Country getCountryById(Integer id);

    boolean updateCountry(Country c);

    boolean insertCountry(Country c);

    boolean deleteCountry(Integer id);
}
