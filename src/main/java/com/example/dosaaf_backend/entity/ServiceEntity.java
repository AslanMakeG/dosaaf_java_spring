package com.example.dosaaf_backend.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "services")
public class ServiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private float cost;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "service")
    private List<RequestEntity> requests;

    @ManyToOne
    @JoinColumn(name = "serviceSection_id")
    private ServiceSectionEntity serviceSection = null;

    public ServiceEntity() {
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

    public ServiceSectionEntity getServiceSection() {
        return serviceSection;
    }

    public void setServiceSection(ServiceSectionEntity serviceSection) {
        this.serviceSection = serviceSection;
    }
}
