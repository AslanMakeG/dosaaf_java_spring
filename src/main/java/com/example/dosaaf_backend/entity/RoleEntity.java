package com.example.dosaaf_backend.entity;

import com.example.dosaaf_backend.enums.ERole;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="roles")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column
    private ERole name;

    public RoleEntity() {}

    public RoleEntity(ERole name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ERole getName() {
        return name;
    }

    public void setName(ERole name) {
        this.name = name;
    }
}