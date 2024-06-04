package com.example.dosaaf_backend.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class MailingGroupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mailingGroup")
    private List<MailingMemberEntity> members;

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

    public List<MailingMemberEntity> getMembers() {
        return members;
    }

    public void setMembers(List<MailingMemberEntity> members) {
        this.members = members;
    }
}
