package com.example.dosaaf_backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "educations")
public class EducationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 80)
    private String name;
    private String file;

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

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getFilePath(){
        if(file == null || id == null) return null;

        return "/education/" + id + "/" + file;
    }
}
