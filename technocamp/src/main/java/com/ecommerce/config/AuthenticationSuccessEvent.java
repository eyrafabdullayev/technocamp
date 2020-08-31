package com.ecommerce.config;

import com.ecommerce.entity.User;
import com.ecommerce.service.inter.UserServiceInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/*
 * by Abdullayev Eyraf
 * email: eyrafabdullayev@gmail.com
 */

@Component
public class AuthenticationSuccessEvent implements ApplicationListener<InteractiveAuthenticationSuccessEvent> {

    @Autowired
    UserServiceInter userService;

    @Override
    public void onApplicationEvent(InteractiveAuthenticationSuccessEvent event) {
        UserDetails userDetails = (UserDetails) event.getAuthentication().getPrincipal();

        //set signed user's details
        User userByUsername = userService.getUserByUsername(userDetails.getUsername());
        if(userByUsername !=null){
            SignedUser.initialize(userByUsername);
        }
    }
}
