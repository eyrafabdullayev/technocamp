package com.ecommerce.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/*
 * by Abdullayev Eyraf
 * email: eyrafabdullayev@gmail.com
 */

@Configuration
public class ApplicationConfig {

    @Value("${application.site-url}")
    private String siteURL;
    @Value("${application.photos-path}")
    private String photos;

    public ApplicationConfig() {
    }

    public String getSiteURL() {
        return siteURL;
    }

    public void setSiteURL(String siteURL) {
        this.siteURL = siteURL;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }
}
