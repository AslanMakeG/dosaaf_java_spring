package com.example.dosaaf_backend.entity;

import jakarta.persistence.*;

@Entity
public class NewsPicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String pictureLink;
    private boolean mainPicture;

    @ManyToOne
    @JoinColumn(name = "news_id")
    private NewsEntity news;

    public NewsPicEntity(){

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

    public boolean isMainPicture() {
        return mainPicture;
    }

    public void setMainPicture(boolean mainPicture) {
        this.mainPicture = mainPicture;
    }

    public NewsEntity getNews() {
        return news;
    }

    public void setNews(NewsEntity news) {
        this.news = news;
    }
}
