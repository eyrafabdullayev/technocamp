package com.ecommerce.controller.cabinet;

import com.ecommerce.config.SignedUser;
import com.ecommerce.entity.User;
import com.ecommerce.form.UserProfileForm;
import com.ecommerce.entity.Country;
import com.ecommerce.service.inter.CountryServiceInter;
import com.ecommerce.service.inter.UserServiceInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UserProfileController {

    @Autowired
    UserServiceInter userService;

    @Autowired
    CountryServiceInter countryService;

    @GetMapping("/cabinet/profile")
    public String profilePage(Model model) {
        User signedUser;
        List<Country> countryList;
        try {
            signedUser = SignedUser.signedUser;

            if (signedUser != null) {
                model.addAttribute("signedUser", signedUser);

                countryList = countryService.getAllCountry();
                if (countryList != null && countryList.size() > 0) {
                    model.addAttribute("countryList", countryList);
                }
            }
        } catch (Exception e) {
            throw new IllegalStateException(e.getLocalizedMessage());
        }

        return "cabinet/profile";
    }

    @PostMapping("/cabinet/profile")
    @ResponseBody
    public String profileUpdate(@Valid @ModelAttribute UserProfileForm userProfileForm, BindingResult result) {

        User signedUser;
        if (!result.hasErrors()) {
            signedUser = SignedUser.signedUser;

            signedUser.setName(userProfileForm.getName());
            signedUser.setSurname(userProfileForm.getSurname());
            signedUser.setUsername(userProfileForm.getUsername());
            signedUser.setPhone(userProfileForm.getPhone());
            signedUser.setCity(userProfileForm.getCity());
            signedUser.setCountry(userProfileForm.getCountry());

            try {
                //update current user's details
                userService.updateUser(signedUser);

                SignedUser.signedUser = signedUser;
            } catch (Exception e){
                return "Internal Server Error";
            }

            return "Operation was successfully!";
        } else {
            return "Please type all the required sections ..";
        }
    }
}
