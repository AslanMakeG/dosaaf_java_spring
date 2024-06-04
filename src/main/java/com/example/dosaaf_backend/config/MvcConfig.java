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
                .addResourceLocations("file://" + announcementUploadPath + "/");

        Path partnerImagesPathDir = Paths.get("./partner");
        String partnerUploadPath = partnerImagesPathDir.toFile().getAbsolutePath();

        registry.addResourceHandler("/partner/**")
                .addResourceLocations("file://" + partnerUploadPath + "/");

        Path educationFilesPathDir = Paths.get("./education");
        String educationUploadPath = educationFilesPathDir.toFile().getAbsolutePath();

        registry.addResourceHandler("/education/**")
                .addResourceLocations("file://" + educationUploadPath + "/");
    }
}
