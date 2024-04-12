package com.example.dosaaf_backend.model;

import com.example.dosaaf_backend.entity.RequestEntity;

import java.util.Date;

public class RequestModel {
    private Long id;
    private String userName;
    private String userSurname;
    private String userPatronymic;
    private String userEmail;
    private Date date;
    private String serviceName;
    private String status;

    public static RequestModel toModel(RequestEntity requestEntity){
        RequestModel model = new RequestModel();
        model.setId(requestEntity.getId());
        model.setStatus(requestEntity.getStatus().getName().name());
        model.setDate(requestEntity.getDate());
        model.setServiceName(requestEntity.getService().getName());

        if(requestEntity.getUser() != null){
            model.setUserName(requestEntity.getUser().getName());
            model.setUserSurname(requestEntity.getUser().getSurname());
            model.setUserPatronymic(requestEntity.getUser().getPatronymic());
            model.setUserEmail(requestEntity.getUser().getEmail());
        }
        else{
            model.setUserName(requestEntity.getUserName());
            model.setUserSurname(requestEntity.getUserSurname());
            model.setUserPatronymic(requestEntity.getUserPatronymic());
            model.setUserEmail(requestEntity.getUserEmail());
        }
        return model;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    public String getUserPatronymic() {
        return userPatronymic;
    }

    public void setUserPatronymic(String userPatronymic) {
        this.userPatronymic = userPatronymic;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
