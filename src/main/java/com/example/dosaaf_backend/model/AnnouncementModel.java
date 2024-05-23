package com.example.dosaaf_backend.model;

import org.springframework.web.multipart.MultipartFile;

public class AnnouncementModel {
    private Long id = null;
    private String title;
    private String content;
    private MultipartFile image = null;
    private boolean sameImage = true;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isSameImage() {
        return sameImage;
    }

    public void setSameImage(boolean sameImage) {
        this.sameImage = sameImage;
    }
}
