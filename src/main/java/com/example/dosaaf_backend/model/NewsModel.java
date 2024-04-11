package com.example.dosaaf_backend.model;

import com.example.dosaaf_backend.entity.NewsEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NewsModel {
    private Long id;
    private String title;
    private String content;
    private Date creationDateTime;
    private boolean inArchive;

    private List<NewsPicModel> pictures;

    public static NewsModel toModel(NewsEntity entity){
        NewsModel model = new NewsModel();
        model.setId(entity.getId());
        model.setTitle(entity.getTitle());
        model.setContent(entity.getContent());
        model.setCreationDateTime(entity.getCreationDateTime());

        List<NewsPicModel> newsPicModels = new ArrayList<>();
        entity.getPictures().forEach(newsPicEntity -> {
            newsPicModels.add(NewsPicModel.toModel(newsPicEntity));
        });

        model.setPictures(newsPicModels);
        return model;
    }

    public NewsModel(){
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Date getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(Date creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public List<NewsPicModel> getPictures() {
        return pictures;
    }

    public void setPictures(List<NewsPicModel> pictures) {
        this.pictures = pictures;
    }

    public boolean isInArchive() {
        return inArchive;
    }

    public void setInArchive(boolean inArchive) {
        this.inArchive = inArchive;
    }
}
