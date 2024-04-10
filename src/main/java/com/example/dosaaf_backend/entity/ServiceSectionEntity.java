package com.example.dosaaf_backend.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class ServiceSectionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "serviceSection")
    private List<ServiceEntity> services = null;

    public ServiceSectionEntity() {
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

    public List<ServiceEntity> getServices() {
        return services;
    }

    public void setServices(List<ServiceEntity> services) {
        this.services = services;
    }
}
