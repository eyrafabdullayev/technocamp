package com.ecommerce.dao.impl;

import com.ecommerce.dao.inter.CategoryDaoInter;
import com.ecommerce.entity.Category;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository("categoryDao")
public class CategoryDaoImpl implements CategoryDaoInter {

    @PersistenceContext
    EntityManager em;
    
    @Override
    public List<Category> getCategoryList() {
        return (List<Category>) em.createQuery("SELECT c FROM Category c")
                .getResultList();
    }

    @Override
    public Category getCategoryById(Integer id) {
        return (Category) em.createQuery("SELECT c FROM Category c WHERE c.id = :id")
                .setParameter("id",id)
                .getSingleResult();
    }

    @Override
    public Category getCategoryByName(String name) {
        return (Category) em.createQuery("SELECT c FROM Category c WHERE c.name = :name")
                .setParameter("name",name)
                .getSingleResult();
    }

    @Override
    public boolean updateCategory(Category c) {
        boolean result = false;
        try {
            em.createQuery("UPDATE Category c SET c.name = :name, c.order = :order, c.status = :status WHERE c.id = :id")
                    .setParameter("name",c.getName())
                    .setParameter("order",c.getOrder())
                    .setParameter("status",c.getStatus())
                    .setParameter("id",c.getId())
                    .executeUpdate();

            result = true;
        } finally {
            return result;
        }
    }

    @Override
    public boolean insertCategory(Category c) {
        boolean result = false;
        try {
            em.createNativeQuery("INSERT INTO category (name,order) VALUES (?,?)")
                    .setParameter(1,c.getName())
                    .setParameter(2,c.getOrder())
                    .executeUpdate();
            result = true;
        } finally {
            return result;
        }
    }

    @Override
    public boolean deleteCategory(Integer id) {
        boolean result = false;
        try {
            em.createQuery("DELETE FROM Category c WHERE c.id = :id")
                    .setParameter("id",id)
                    .executeUpdate();
            result = true;
        } finally {
            return result;
        }
    }
    
}
