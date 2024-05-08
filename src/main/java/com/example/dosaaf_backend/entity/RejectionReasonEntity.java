package com.example.dosaaf_backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "rejection_reasons")
public class RejectionReasonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String description;
    @OneToOne(mappedBy = "rejectionReason")
    private RequestEntity request;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RequestEntity getRequest() {
        return request;
    }

    public void setRequest(RequestEntity request) {
        this.request = request;
    }
}
