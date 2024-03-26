package com.example.dosaaf_backend.model;

import com.example.dosaaf_backend.entity.NewsEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class News {
    private Long id;
    private String title;
    private String content;
    private Date creationDateTime;

    public static News toModel(NewsEntity entity){
        News model = new News();
        model.setId(entity.getId());
        model.setTitle(entity.getTitle());
        model.setContent(entity.getContent());
        model.setCreationDateTime(entity.getCreationDateTime());
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
}
