package com.ecommerce.service.impl;

import com.ecommerce.entity.User;
import com.ecommerce.security.ApplicationUserRole;
import com.ecommerce.service.inter.UserServiceInter;
import com.ecommerce.service.inter.UserDetailsServiceInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User.UserBuilder;

import static com.ecommerce.security.ApplicationUserRole.ADMIN;
import static com.ecommerce.security.ApplicationUserRole.USER;

@Service
public class UserDetailServiceImpl implements UserDetailsServiceInter {

    @Autowired
    private UserServiceInter userServiceImpl;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = userServiceImpl.getUserByUsername(username);

        UserBuilder builder = null;
        if(u!=null){
            builder = org.springframework.security.core.userdetails.User.withUsername(username);

            boolean status = u.getStatus() == 1 ? false : true;
            builder.disabled(status);
            builder.password(u.getPassword());
            ApplicationUserRole userRole = u.getRole().equals("ADMIN") ? ADMIN : USER;
            builder.authorities(userRole.getAuthorities());

            return builder.build();
        }else{
            throw new UsernameNotFoundException("User could not found!");
        }
    }
}
