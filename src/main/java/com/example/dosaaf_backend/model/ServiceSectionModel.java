package com.example.dosaaf_backend.model;

import com.example.dosaaf_backend.entity.ServiceSectionEntity;

import java.util.ArrayList;
import java.util.List;

public class ServiceSectionModel {
    private Long id;
    private String name;
    private List<ServiceModel> services;

    public static ServiceSectionModel toModel(ServiceSectionEntity serviceSectionEntity){
        ServiceSectionModel model = new ServiceSectionModel();
        model.setId(serviceSectionEntity.getId());
        model.setName(serviceSectionEntity.getName());

        List<ServiceModel> services = new ArrayList<>();

        serviceSectionEntity.getServices().forEach(serviceEntity -> {
            services.add(ServiceModel.toModel(serviceEntity));
        });

        model.setServices(services);
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

    public List<ServiceModel> getServices() {
        return services;
    }

    public void setServices(List<ServiceModel> services) {
        this.services = services;
    }
}