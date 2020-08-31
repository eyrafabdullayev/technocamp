package com.ecommerce.controller.cabinet;

import com.ecommerce.config.ApplicationConfig;
import com.ecommerce.config.SignedUser;
import com.ecommerce.form.UserStoreForm;
import com.ecommerce.entity.Store;
import com.ecommerce.entity.User;
import com.ecommerce.service.inter.StoreServiceInter;
import com.ecommerce.service.inter.UserServiceInter;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
public class UserStoreController {

    @Autowired
    ApplicationConfig config;

    @Autowired
    StoreServiceInter storeService;

    @Autowired
    UserServiceInter userService;

    @GetMapping("/cabinet/store")
    public String storePage(Model model) {

        User userByUsername;
        try {
            userByUsername = userService.getUserById(SignedUser.signedUser.getId());
            if (userByUsername != null) {
                model.addAttribute("signedUser", userByUsername);

                Store storeByUserId = userByUsername.getStore();
                if (storeByUserId != null) {
                    model.addAttribute("storeByUserId", storeByUserId);
                    model.addAttribute("status", storeByUserId.getStatus());
                } else {
                    model.addAttribute("status", 0);
                }
            }
        } catch (Exception e) {

        } finally {
            return "cabinet/store";
        }
    }

    @PostMapping("/cabinet/store")
    @ResponseBody
    public String storeAdd(@Valid @ModelAttribute UserStoreForm storeForm,
                           BindingResult result) {

        MultipartFile file = storeForm.getImage();

        if (!result.hasErrors()) {
            if (file != null && !file.isEmpty()) {
                int maxSize = 1024 * 1024 * 5; //5 MB
                if (maxSize < file.getSize()) {
                    return "image size cannot be bigger than 5 MB";
                }

                String[] allowedExtensions = {
                        "image/jpeg",
                        "image/jpg",
                        "image/png"
                };

                String type = null;
                for (String extension : allowedExtensions) {
                    if (extension.equals(file.getContentType())) {
                        type = extension.equals("image/jpeg") ? ".jpeg" : (extension.equals("image/jpg") ? ".jpg" : ".png");
                    }
                }

                if (type == null) {
                    return ".PNG,.JPEG,.JPG allowed!";
                }

                String uuid = UUID.randomUUID().toString();
                String fileName = "store-" + uuid + type;

                String folder = config.getPhotos();
                try {
                    Path path = Paths.get(folder + fileName);
                    Files.write(path, file.getBytes());

                    Integer userId = storeForm.getUserId();
                    String name = storeForm.getName();
                    String description = storeForm.getDescription();
                    String phone = storeForm.getPhone();
                    String location = storeForm.getLocation();

                    //insert store
                    Store store = new Store(null,SignedUser.signedUser,name,phone,description,location,fileName);
                    storeService.insertStore(store);

                    SignedUser.signedUser.setStore(store);
                } catch (Exception e) {
                    return "Internal Server Error";
                }
            } else {
                return "Plese type all required fields!";
            }
        } else {
            return "Please type all required fields!";
        }
        return "Operation was successfully! Your request will be accepted about few hours by moderator.";
    }

    @PostMapping("/cabinet/store/{storeId}")
    @ResponseBody
    public String storeUpdate(@Valid @ModelAttribute UserStoreForm storeForm,
                              @PathVariable("storeId") Integer storeId,
                              BindingResult result) {

        MultipartFile file = storeForm.getImage();

        if(!result.hasErrors()) {

            Store store = SignedUser.signedUser.getStore();

            String newFileName = null;
            if(file != null) {

                if(!file.isEmpty()) {
                    int maxSize = 1024 * 1024 * 5; //5 MB
                    if (maxSize < file.getSize()) {
                        return "image size cannot be bigger than 5 MB";
                    }

                    String[] allowedExtensions = {
                            "image/jpeg",
                            "image/jpg",
                            "image/png"
                    };

                    String type = null;

                    for (String extension : allowedExtensions) {
                        if (extension.equals(file.getContentType())) {
                            type = extension.equals("image/jpeg") ? ".jpeg" : (extension.equals("image/jpg") ? ".jpg" : ".png");
                        }
                    }

                    if (type == null) {
                        return ".PNG,.JPEG,.JPG allowed!";
                    }

                    String uuid = UUID.randomUUID().toString();
                    newFileName = "store-" + uuid + type;

                    String folder = config.getPhotos();
                    try {
                        Path path = Paths.get(folder + "/" + newFileName);
                        Files.write(path,file.getBytes());

                        //if there is a new image file delete old image
                        new File(config.getPhotos() + "/" + store.getImageURL()).delete();

                        store.setImageURL(newFileName);
                    } catch (Exception e) {
                        return e.getLocalizedMessage();
                    }
                }
            }

            try {
                //get values
                String name = storeForm.getName();
                String description = storeForm.getDescription();
                String phone = storeForm.getPhone();
                String location = storeForm.getLocation();

                //update values
                store.setName(name);
                store.setDescription(description);
                store.setPhone(phone);
                store.setLocation(location);

                store.setStatus(Short.valueOf("1"));
                //now if everything is success update store
                storeService.updateStore(store);

            } catch (Exception e) {
                return e.getLocalizedMessage();
            }
        } else {
            return "Please change all sections without any mistake! ";
        }
        return "success";
    }
}
