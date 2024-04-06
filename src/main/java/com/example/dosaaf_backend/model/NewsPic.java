package com.example.dosaaf_backend.model;

import com.example.dosaaf_backend.entity.NewsPicEntity;

public class NewsPic {
    private Long id;
    private String pictureLink;

    public static NewsPic toModel(NewsPicEntity newsPicture){
        NewsPic newPic = new NewsPic();
        newPic.setId(newsPicture.getId());
        newPic.setPictureLink(newsPicture.getPictureLink());
        return newPic;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPictureLink() {
        return pictureLink;
    }

    public void setPictureLink(String pictureLink) {
        this.pictureLink = pictureLink;
    }
}
