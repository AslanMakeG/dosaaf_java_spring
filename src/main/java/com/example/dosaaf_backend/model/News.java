package com.example.dosaaf_backend.model;

import com.example.dosaaf_backend.entity.NewsEntity;
import com.example.dosaaf_backend.entity.NewsPicEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class News {
    private Long id;
    private String title;
    private String content;
    private Date creationDateTime;
    private boolean inArchive;

    private List<NewsPic> pictures;

    public static News toModel(NewsEntity entity){
        News model = new News();
        model.setId(entity.getId());
        model.setTitle(entity.getTitle());
        model.setContent(entity.getContent());
        model.setCreationDateTime(entity.getCreationDateTime());

        List<NewsPic> newsPics = new ArrayList<>();
        entity.getPictures().forEach(newsPicEntity -> {
            newsPics.add(NewsPic.toModel(newsPicEntity));
        });

        model.setPictures(newsPics);
        return model;
    }

    public News(){
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

    public List<NewsPic> getPictures() {
        return pictures;
    }

    public void setPictures(List<NewsPic> pictures) {
        this.pictures = pictures;
    }

    public boolean isInArchive() {
        return inArchive;
    }

    public void setInArchive(boolean inArchive) {
        this.inArchive = inArchive;
    }
}
