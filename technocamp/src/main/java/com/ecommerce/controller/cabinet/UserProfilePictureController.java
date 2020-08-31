package com.ecommerce.controller.cabinet;

import com.ecommerce.config.ApplicationConfig;
import com.ecommerce.config.SignedUser;
import com.ecommerce.entity.User;
import com.ecommerce.service.inter.UserServiceInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
public class UserProfilePictureController {

    @Autowired
    ApplicationConfig config;

    @Autowired
    UserServiceInter userService;

    @GetMapping("/cabinet/change-profile-picture")
    public String profilePictureUploadPage(Model model) {
        User signedUser;
        try {
            signedUser = SignedUser.signedUser;

            if (signedUser != null) {
                model.addAttribute("signedUser", signedUser);
            }
        } catch (Exception e) {
            throw new IllegalStateException(e.getLocalizedMessage());
        }

        return "change-profile-picture";
    }

    @PostMapping("/cabinet/change-profile-picture")
    @ResponseBody
    public String profilePictureUpload(@RequestParam("image") MultipartFile file,
                                       @RequestParam Integer id) throws IOException {
        User signedUser;
        if (file != null) {

            if(!file.isEmpty()) {
                int maxSize = 1024*1024*5; //5 MB
                if(maxSize < file.getSize()){
                    return "image size must not be bigger than 5 MB";
                }

                String[] allowedExtensions = {
                        "image/jpeg",
                        "image/jpg",
                        "image/png"
                };

                String type = null;
                for(String extension : allowedExtensions){
                    if(extension.equals(file.getContentType())){
                        type = extension.equals("image/jpeg") ? ".jpeg" : (extension.equals("image/jpg") ? ".jpg" : ".png");
                    }
                }

                if(type == null){
                    return ".PNG,.JPEG,.JPG allowed!";
                }

                if(id == null){
                    return "id must not be empty!";
                }

                String uuid = UUID.randomUUID().toString();
                String fileName = "avatar-" + uuid + type;

                String folder = config.getPhotos();
                try {
                    Path path = Paths.get(folder + fileName);
                    Files.write(path,file.getBytes());

                    signedUser = SignedUser.signedUser;
                    //delete current profile picture
                    String currentProfilePicture = signedUser.getPicture();
                    if(currentProfilePicture != null && !"avatar-default".equals(currentProfilePicture)){
                        if(!currentProfilePicture.isEmpty()){
                            new File(folder+currentProfilePicture).delete();
                        }
                    }

                    signedUser.setPicture(fileName);
                    //update active user's information
                    userService.updateUser(signedUser);

                    SignedUser.signedUser = signedUser;
                } catch (Exception e){
                    return "Internal Server Error";
                }
            }

        } else {
            return "image must not be empty!";
        }

        return "Operation was successfully!";
    }
}
