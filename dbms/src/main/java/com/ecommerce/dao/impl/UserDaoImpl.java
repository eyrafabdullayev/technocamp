package com.ecommerce.dao.impl;

import com.ecommerce.dao.inter.UserDaoInter;
import com.ecommerce.entity.User;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository("userDao")
public class UserDaoImpl implements UserDaoInter {

    @PersistenceContext
    EntityManager em;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public List<User> getAllUser() {
        return em.createQuery("SELECT u FROM User u")
                .getResultList();
    }

    @Override
    public User getUserById(Integer id) {
        return (User) em.createQuery("SELECT u FROM User u WHERE u.id = :id")
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public User getUserByUsername(String username) {
        return (User) em.createQuery("SELECT u FROM User u WHERE u.username = :username")
                .setParameter("username", username)
                .getSingleResult();
    }



    @Override
    public boolean updateUser(User u) {
        boolean result = false;
        try {
            em.createQuery("UPDATE User u SET u.name = :name, u.surname = :surname, u.username = :username, u.phone = :phone, u.picture = :picture, u.city = :city, u.country = :country, u.password = :password, u.role = :role, u.status = :status WHERE u.id = :id")
                    .setParameter("name", u.getName())
                    .setParameter("surname", u.getSurname())
                    .setParameter("username", u.getUsername())
                    .setParameter("phone", u.getPhone())
                    .setParameter("picture", u.getPicture())
                    .setParameter("city", u.getCity())
                    .setParameter("country", u.getCountry())
                    .setParameter("password", u.getPassword())
                    .setParameter("role", u.getRole())
                    .setParameter("status", u.getStatus())
                    .setParameter("id", u.getId())
                    .executeUpdate();
            
            result = true;
        } catch (Exception e) {
            throw new IllegalStateException("Internal Server Error");
        } finally {
            return result;
        }
    }

    @Override
    public boolean insertUser(User u) {
        boolean result = false;
        try {
            em.createNativeQuery("INSERT INTO user (name,surname,username,phone,city,country,type,password) VALUES(?,?,?,?,?,?,?,?)")
                    .setParameter(1, u.getName())
                    .setParameter(2, u.getSurname())
                    .setParameter(3, u.getUsername())
                    .setParameter(4, u.getPhone())
                    .setParameter(5, u.getCity())
                    .setParameter(6, u.getCountry())
                    .setParameter(7, u.getType())
                    .setParameter(8, passwordEncoder.encode(u.getPassword()))
                    .executeUpdate();
            
            result = true;
        } catch(Exception e){
            throw new IllegalStateException("Internal Server Error");
        } finally {
            return result;
        }
    }

    @Override
    public boolean deleteUser(Integer id) {
        boolean result = false;
        try {
            em.createQuery("DELETE FROM User u WHERE u.id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
            
            result = true;
        } catch(Exception e){
            
        } finally {
            return result;
        }
    }

}
