package com.example.dosaaf_backend.model;

import com.example.dosaaf_backend.entity.MailingGroupEntity;
import com.example.dosaaf_backend.entity.MailingMemberEntity;

import java.util.ArrayList;
import java.util.List;

public class MailingGroupModel {
    private Long id = null;
    private String name;
    private List<MailingMemberModel> members = null;

    public static MailingGroupModel toModel(MailingGroupEntity mailingGroupEntity){
        MailingGroupModel model = new MailingGroupModel();

        model.setName(mailingGroupEntity.getName());
        model.setId(mailingGroupEntity.getId());
        if(mailingGroupEntity.getMembers() != null){
            List<MailingMemberModel> mailingMemberModels = new ArrayList<>();
            for(MailingMemberEntity mailingMemberEntity : mailingGroupEntity.getMembers()){
                mailingMemberModels.add(MailingMemberModel.toModel(mailingMemberEntity));
            }
            model.setMembers(mailingMemberModels);
        }

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

    public List<MailingMemberModel> getMembers() {
        return members;
    }

    public void setMembers(List<MailingMemberModel> members) {
        this.members = members;
    }
}
