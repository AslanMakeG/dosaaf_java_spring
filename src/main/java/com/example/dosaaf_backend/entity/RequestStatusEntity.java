package com.example.dosaaf_backend.entity;

import com.example.dosaaf_backend.enums.EStatus;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "request_status")
public class RequestStatusEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column
    private EStatus name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "status")
    private List<RequestEntity> requests;

    public RequestStatusEntity() {
    }

    public RequestStatusEntity(EStatus name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EStatus getName() {
        return name;
    }

    public void setName(EStatus name) {
        this.name = name;
    }

    public List<RequestEntity> getRequests() {
        return requests;
    }

    public void setRequests(List<RequestEntity> requests) {
        this.requests = requests;
    }
}
