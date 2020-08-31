package com.ecommerce.service.impl;

import com.ecommerce.dao.inter.CountryDaoInter;
import com.ecommerce.entity.Country;
import com.ecommerce.service.inter.CountryServiceInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CountryServiceImpl implements CountryServiceInter {

    @Autowired
    @Qualifier("countryDao")
    CountryDaoInter countryDao;

    @Override
    public List<Country> getAllCountry() {
        return countryDao.getAllCountry();
    }

    @Override
    public Country getCountryById(Integer id) {
        return countryDao.getCountryById(id);
    }

    @Override
    public boolean updateCountry(Country c) {
        return countryDao.updateCountry(c);
    }

    @Override
    public boolean insertCountry(Country c) {
        return countryDao.insertCountry(c);
    }

    @Override
    public boolean deleteCountry(Integer id) {
        return countryDao.deleteCountry(id);
    }
}
