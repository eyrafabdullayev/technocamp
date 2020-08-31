package com.ecommerce.config;

import com.ecommerce.entity.User;
import org.springframework.stereotype.Component;

/*
 * by Abdullayev Eyraf
 * email: eyrafabdullayev@gmail.com
 */

@Component
public class SignedUser {

    public static User signedUser;

    private SignedUser(){}

    public static User initialize(User user){
        if(signedUser == null){
            signedUser = user;
        }
        return signedUser;
    }
}
