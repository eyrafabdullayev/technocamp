package com.ecommerce.service.impl;

import com.ecommerce.dao.inter.UserDaoInter;
import com.ecommerce.entity.User;
import java.util.List;

import com.ecommerce.service.inter.UserServiceInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserServiceInter {

    @Autowired
    @Qualifier("userDao")
    UserDaoInter userDaoImpl;
    
    @Override
    public List<User> getAllUser() {
        return userDaoImpl.getAllUser();
    }

    @Override
    public User getUserById(Integer id) {
        return userDaoImpl.getUserById(id);
    }

    @Override
    public User getUserByUsername(String username) {
        return userDaoImpl.getUserByUsername(username);
    }

    @Override
    public boolean updateUser(User u) {
        return userDaoImpl.updateUser(u);
    }

    @Override
    public boolean insertUser(User u) {
        return userDaoImpl.insertUser(u);
    }

    @Override
    public boolean deleteUser(Integer id) {
        return userDaoImpl.deleteUser(id);
    }  
}
