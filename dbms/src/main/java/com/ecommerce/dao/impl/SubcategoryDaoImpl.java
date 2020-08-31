package com.ecommerce.dao.impl;

import com.ecommerce.dao.inter.SubcategoryDaoInter;
import com.ecommerce.entity.Subcategory;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository("subcategoryDao")
public class SubcategoryDaoImpl implements SubcategoryDaoInter {
    
    @PersistenceContext
    EntityManager em;
    
    @Override
    public List<Subcategory> getSubcategoryList() {
        return (List<Subcategory>) em.createQuery("SELECT s FROM Subcategory s")
                .getResultList();
    }
    
    @Override
    public List<Subcategory> getSubcategoriesByCategoryId(Integer id) {
        return (List<Subcategory>) em.createQuery("SELECT s FROM Subcategory s WHERE s.categoryId = :categoryId")
                .setParameter("categoryId", id)
                .getResultList();
    }

    @Override
    public Subcategory getSubcategoryById(Integer id) {
        return (Subcategory) em.createQuery("SELECT s FROM Subcategory s WHERE s.id = :id")
                .setParameter("id",id)
                .getSingleResult();
    }

    @Override
    public boolean updateSubcategory(Subcategory s) {
        boolean result = false;
        try {
            em.createQuery("UPDATE Subcategory s SET s.name = :name, s.categoryId = :categoryId, s.order = :order, s.status = :status WHERE  s.id = :id")
                    .setParameter("name",s.getName())
                    .setParameter("categoryId",s.getCategoryId())
                    .setParameter("order",s.getOrder())
                    .setParameter("status",s.getStatus())
                    .setParameter("id", s.getId())
                    .executeUpdate();

            result = true;
        } finally {
            return result;
        }
    }

    @Override
    public boolean insertSubcategory(Subcategory s) {
        boolean result = false;
        try {
            em.createNativeQuery("INSERT INTO subcategory (name,category_id,order) VALUES (?,?,?)")
                    .setParameter(1,s.getName())
                    .setParameter(2,s.getCategoryId())
                    .setParameter(3,s.getOrder())
                    .executeUpdate();

            result = true;
        } finally {
            return result;
        }
    }

    @Override
    public boolean deleteSubcategory(Integer id) {
        boolean result = false;
        try {
            em.createQuery("DELETE FROM Subcategory s WHERE s.id = :id")
                    .setParameter("id",id)
                    .executeUpdate();

            result = true;
        } finally {
            return result;
        }
    }
    
}
