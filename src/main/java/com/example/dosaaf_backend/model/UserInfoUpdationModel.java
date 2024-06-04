package com.example.dosaaf_backend.model;

public class UserInfoUpdationModel {
    private String name;
    private String surname;
    private String patronymic;
    private Boolean subscribedForNews;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public Boolean getSubscribedForNews() {
        return subscribedForNews;
    }

    public void setSubscribedForNews(Boolean subscribedForNews) {
        this.subscribedForNews = subscribedForNews;
    }
}
