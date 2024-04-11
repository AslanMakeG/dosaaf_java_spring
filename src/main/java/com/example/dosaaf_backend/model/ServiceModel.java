package com.example.dosaaf_backend.model;

import com.example.dosaaf_backend.entity.ServiceEntity;

public class ServiceModel {
    private Long id;
    private String name;
    private String description;
    private float cost;
    private Long sectionId;

    public static ServiceModel toModel(ServiceEntity serviceEntity){
        ServiceModel model = new ServiceModel();
        model.setId(serviceEntity.getId());
        model.setName(serviceEntity.getName());
        model.setDescription(serviceEntity.getDescription());
        model.setCost(serviceEntity.getCost());
        model.setSectionId(serviceEntity.getServiceSection().getId());
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }
}
