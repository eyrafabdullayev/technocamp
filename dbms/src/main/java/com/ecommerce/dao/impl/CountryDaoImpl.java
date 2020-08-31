package com.ecommerce.dao.impl;

import com.ecommerce.dao.inter.CountryDaoInter;
import com.ecommerce.entity.Country;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository("countryDao")
public class CountryDaoImpl implements CountryDaoInter {

    @PersistenceContext
    EntityManager em;

    @Override
    public List<Country> getAllCountry() {
        return em.createQuery("SELECT c FROM Country c")
                .getResultList();
    }

    @Override
    public Country getCountryById(Integer id) {
        return (Country) em.createQuery("SELECT c FROM Country c WHERE c.id = :id")
                .setParameter("id",id)
                .getSingleResult();
    }

    @Override
    public boolean updateCountry(Country c) {
        boolean result = false;
        try {
            em.createQuery("UPDATE Country c SET c.name = :name WHERE c.id = :id")
                    .setParameter("name", c.getName())
                    .setParameter("id", c.getId())
                    .executeUpdate();
            result = true;
        } catch (Exception e){
            throw new IllegalStateException("Internal Server Error");
        } finally {
            return result;
        }
    }

    @Override
    public boolean insertCountry(Country c) {
        boolean result = false;
        try {
            em.createNativeQuery("INSERT INTO country (name,`order`) VALUES (?,?)")
                    .setParameter(1, c.getName())
                    .setParameter(2, c.getOrder())
                    .executeUpdate();
            result = true;
        } catch (Exception e){
            throw new IllegalStateException("Internal Server Error");
        } finally {
            return result;
        }
    }

    @Override
    public boolean deleteCountry(Integer id) {
        boolean result = false;
        try {
            em.createQuery("DELETE From Country c WHERE c.id = :id")
                    .setParameter("id",id)
                    .executeUpdate();
            result = true;
        } catch (Exception e){
            throw new IllegalStateException("Internal Server Error");
        } finally {
            return result;
        }
    }
}
