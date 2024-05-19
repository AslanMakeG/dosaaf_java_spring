package com.example.dosaaf_backend.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "news")
public class NewsEntity {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    @Column
    private String title;
    @Column(length = 15000)
    private String content;
    @Column
    private String albumLink = null;
    @Column
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date creationDateTime = new Date();
    @Column
    private boolean inArchive = false;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "news")
    private List<NewsPicEntity> pictures = new ArrayList<>();

    public NewsEntity(){

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

    public String getAlbumLink() {
        return albumLink;
    }

    public void setAlbumLink(String albumLink) {
        this.albumLink = albumLink;
    }

    public Date getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(Date creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public boolean isInArchive() {
        return inArchive;
    }

    public void setInArchive(boolean inArchive) {
        this.inArchive = inArchive;
    }

    public List<NewsPicEntity> getPictures() {
        return pictures;
    }

    public void setPictures(List<NewsPicEntity> pictures) {
        this.pictures = pictures;
    }

    public NewsPicEntity getMainPicture(){
        return pictures.stream().filter(picture -> picture.isMainPicture()).findAny().orElse(null);
    }
}
