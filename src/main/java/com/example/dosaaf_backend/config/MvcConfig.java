package com.example.dosaaf_backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path announcementImagesPathDir = Paths.get("./announcement");
        String announcementUploadPath = announcementImagesPathDir.toFile().getAbsolutePath();

        registry.addResourceHandler("/announcement/**")
                .addResourceLocations("file:/" + announcementUploadPath + "/");
    }
}