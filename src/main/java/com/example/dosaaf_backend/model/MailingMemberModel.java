package com.example.dosaaf_backend.model;

import com.example.dosaaf_backend.entity.MailingMemberEntity;

public class MailingMemberModel {
    private Long id = null;
    private String name;
    private String email;
    private Long groupId;

    public static MailingMemberModel toModel(MailingMemberEntity mailingMemberEntity){
        MailingMemberModel model = new MailingMemberModel();

        model.setId(mailingMemberEntity.getId());
        model.setName(mailingMemberEntity.getName());
        model.setEmail(mailingMemberEntity.getEmail());
        model.setGroupId(mailingMemberEntity.getMailingGroup().getId());

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
}
