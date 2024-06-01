package com.example.dosaaf_backend.security.Pojo;

import java.util.Set;

public class SingupRequest {
    private String name;
    private String surname;
    private String patronymic;
    private String email;
    private String password;
    private Set<String> roles;
    private boolean subscribedForNews = true;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public boolean isSubscribedForNews() {
        return subscribedForNews;
    }

    public void setSubscribedForNews(boolean subscribedForNews) {
        this.subscribedForNews = subscribedForNews;
    }
}
