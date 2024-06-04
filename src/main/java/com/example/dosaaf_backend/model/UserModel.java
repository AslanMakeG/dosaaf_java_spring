package com.example.dosaaf_backend.model;

import com.example.dosaaf_backend.entity.RoleEntity;
import com.example.dosaaf_backend.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class UserModel {
    private Long id;
    private String name;
    private String surname;
    private String patronymic;
    private String email;
    private Date registrationDate;
    private Set<RoleEntity> roles;
    private Boolean subscribedForNews;

    public static UserModel toModel(UserEntity user){
        UserModel model = new UserModel();
        model.setId(user.getId());
        model.setName(user.getName());
        model.setSurname(user.getSurname());
        model.setPatronymic(user.getPatronymic());
        model.setEmail(user.getEmail());
        model.setRegistrationDate(user.getRegistrationDate());
        model.setRoles(user.getRoles());
        return model;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Set<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
    }

    public Boolean getSubscribedForNews() {
        return subscribedForNews;
    }

    public void setSubscribedForNews(Boolean subscribedForNews) {
        this.subscribedForNews = subscribedForNews;
    }
}
