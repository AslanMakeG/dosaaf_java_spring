package com.example.dosaaf_backend.security.Pojo;

import com.example.dosaaf_backend.entity.RoleEntity;

import java.util.List;

public class JwtResponse {
    private String token;
    private String type  = "Bearer";
    private Long id;
    private String email;
    private String name;
    private String surname;
    private String patronymic;
    private List<String> roles;

    public JwtResponse(String token, Long id, String email, String name, String surname, String patronymic, List<String> roles) {
        this.token = token;
        this.id = id;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.roles = roles;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }
}
