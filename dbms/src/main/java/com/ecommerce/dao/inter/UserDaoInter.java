package com.ecommerce.dao.inter;

import com.ecommerce.entity.User;
import java.util.List;

public interface UserDaoInter {
    
    List<User> getAllUser();
    
    User getUserById(Integer id);
    
    User getUserByUsername(String username);
    
    boolean updateUser(User u);
    
    boolean insertUser(User u);
    
    boolean deleteUser(Integer id);
}
