package com.example.dosaaf_backend.model;

import com.example.dosaaf_backend.entity.NewsPicEntity;

public class NewsPicModel {
    private Long id;
    private String pictureLink;
    private Long newsId;

    public static NewsPicModel toModel(NewsPicEntity newsPicture){
        NewsPicModel newPic = new NewsPicModel();
        newPic.setId(newsPicture.getId());
        newPic.setPictureLink(newsPicture.getPictureLink());
        newPic.setNewsId(newsPicture.getNews().getId());
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

    public Long getNewsId() {
        return newsId;
    }

    public void setNewsId(Long newsId) {
        this.newsId = newsId;
    }
}
