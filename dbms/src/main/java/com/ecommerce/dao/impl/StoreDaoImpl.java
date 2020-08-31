package com.ecommerce.dao.impl;

import com.ecommerce.dao.inter.StoreDaoInter;
import com.ecommerce.entity.Store;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository("storeDao")
public class StoreDaoImpl implements StoreDaoInter {

    @PersistenceContext
    EntityManager em;

    @Override
    public List<Store> getAllStories() {
        return (List<Store>) em.createQuery("SELECT s FROM Store s")
                .getResultList();
    }

    @Override
    public Store getStoreById(Integer id) {
        return (Store) em.createQuery("SELECT s FROM Store s WHERE s.id = :id")
                .setParameter("id",id)
                .getSingleResult();
    }

    @Override
    public Store getStoreByUserId(Integer id) {
        return (Store) em.createQuery("SELECT s FROM Store s WHERE s.user.id = :id")
                .setParameter("id",id)
                .getSingleResult();
    }

    @Override
    public boolean updateStore(Store s) {
        boolean result = false;
        try {
            em.createQuery("UPDATE Store s SET s.name = :name, s.description = :description, s.phone = :phone, s.location = :location, s.imageURL = :imageURL WHERE s.id = :id")
                    .setParameter("name",s.getName())
                    .setParameter("description",s.getDescription())
                    .setParameter("phone",s.getPhone())
                    .setParameter("location",s.getLocation())
                    .setParameter("imageURL",s.getImageURL())
                    .setParameter("id",s.getId())
                    .executeUpdate();

            result = true;
        } finally {
            return result;
        }
    }

    @Override
    public boolean insertStore(Store s) {
        boolean result = false;
        try {
           em.createNativeQuery("INSERT INTO store (user_id,name,description,phone,location,imageURL) VALUES (?,?,?,?,?,?)")
                   .setParameter(1,s.getUser().getId())
                   .setParameter(2,s.getName())
                   .setParameter(3,s.getDescription())
                   .setParameter(4,s.getPhone())
                   .setParameter(5,s.getLocation())
                   .setParameter(6,s.getImageURL())
                   .executeUpdate();

            result = true;
        } finally {
            return result;
        }
    }

    @Override
    public boolean deleteStore(Integer id) {
        boolean result = false;
        try {
            em.createQuery("DELETE FROM Store s WHERE s.id = :id")
                    .setParameter("id",id)
                    .executeUpdate();

            result = true;
        } finally {
            return result;
        }
    }
}
