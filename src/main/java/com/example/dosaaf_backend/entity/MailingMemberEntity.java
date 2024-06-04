package com.example.dosaaf_backend.entity;

import jakarta.persistence.*;

@Entity
public class MailingMemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    @ManyToOne
    @JoinColumn(name = "mailingGroup_id")
    private MailingGroupEntity mailingGroup;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public MailingGroupEntity getMailingGroup() {
        return mailingGroup;
    }

    public void setMailingGroup(MailingGroupEntity mailingGroup) {
        this.mailingGroup = mailingGroup;
    }
}
