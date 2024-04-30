package com.example.dosaaf_backend.model;

import org.springframework.web.multipart.MultipartFile;

public class AnnouncementCreationModel {
    private String title;
    private String content;
    private MultipartFile image;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
