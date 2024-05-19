package com.example.dosaaf_backend.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import org.springframework.web.multipart.MultipartFile;

public class PartnerCreationModel {
    private Long id;
    private String name;
    private String link;
    private MultipartFile image = null;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImagePath(){
        if(image == null || id == null) return null;

        return "/partner/" + id + "/" + image;
    }
}
