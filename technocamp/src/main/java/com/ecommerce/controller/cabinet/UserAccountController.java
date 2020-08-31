package com.ecommerce.controller.cabinet;

import com.ecommerce.config.SignedUser;
import com.ecommerce.entity.User;
import com.ecommerce.form.UserAccountForm;
import com.ecommerce.service.inter.UserServiceInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class UserAccountController {

    @Autowired
    UserServiceInter userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/cabinet/account")
    public String accountPage(Model model) {
        User signedUser;
        try {
            signedUser = SignedUser.signedUser;

            if (signedUser != null) {
                model.addAttribute("signedUser", signedUser);
            }
        } catch (Exception e) {
            throw new IllegalStateException(e.getLocalizedMessage());
        }

        return "cabinet/account";
    }

    @PostMapping("/cabinet/account")
    @ResponseBody
    public String accountUpdate(@Valid @ModelAttribute UserAccountForm accountForm, BindingResult result) {


        if (!result.hasErrors()) {

            String currentPassword = accountForm.getCurrentPassword();
            String newPassword = accountForm.getNewPassword();
            String newPasswordAgain = accountForm.getNewPasswordAgain();

            if (newPassword.equals(newPasswordAgain)) {
                String password = newPassword;
                if (password.toCharArray().length < 6) {
                    return "Password cannot be less than 6 character!";
                }

                if (password.equals(currentPassword)) {
                    return "Your password which you tried to change, equals your new password!";
                }

                User currentUser = userService.getUserById(accountForm.getId());
                if (currentUser == null) {
                    return "User cannot found!";
                }

                if (!passwordEncoder.matches(currentPassword, currentUser.getPassword())) {
                    return "Current Password is not valid!";
                }

                //new encoded password
                String encodedPassword = passwordEncoder.encode(password);

                currentUser.setPassword(encodedPassword);

                try {
                    //update user
                    userService.updateUser(currentUser);

                    SignedUser.signedUser = currentUser;
                } catch (Exception e) {
                    return "Internal Server Error";
                }
            } else {
                return "Passwords must be equal!";
            }

        } else {
            return "Please type required sections!";
        }
        return "success";
    }
}
