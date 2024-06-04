package com.example.dosaaf_backend.service;

import com.example.dosaaf_backend.entity.MailingGroupEntity;
import com.example.dosaaf_backend.entity.MailingMemberEntity;
import com.example.dosaaf_backend.exception.mailing.MailingGroupNotFoundException;
import com.example.dosaaf_backend.exception.mailing.MailingMemberNotFoundException;
import com.example.dosaaf_backend.model.MailingGroupModel;
import com.example.dosaaf_backend.model.MailingMemberModel;
import com.example.dosaaf_backend.repository.MailingGroupRepo;
import com.example.dosaaf_backend.repository.MailingMemberRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MailingService {
    @Autowired
    private MailingGroupRepo mailingGroupRepo;

    @Autowired
    private MailingMemberRepo mailingMemberRepo;

    public MailingGroupModel createGroup(MailingGroupModel mailingGroupModel){
        MailingGroupEntity mailingGroupEntity = new MailingGroupEntity();
        mailingGroupEntity.setName(mailingGroupModel.getName());
        return MailingGroupModel.toModel(mailingGroupRepo.save(mailingGroupEntity));
    }


    public MailingMemberModel createMember(MailingMemberModel mailingMemberModel) throws MailingGroupNotFoundException {
        MailingMemberEntity mailingMemberEntity = new MailingMemberEntity();

        mailingMemberEntity.setMailingGroup(mailingGroupRepo.findById(mailingMemberModel.getGroupId()).orElseThrow(
                () -> new MailingGroupNotFoundException("Группа рассылки не найдена")
        ));

        mailingMemberEntity.setName(mailingMemberModel.getName());
        mailingMemberEntity.setEmail(mailingMemberModel.getEmail());

        return MailingMemberModel.toModel(mailingMemberRepo.save(mailingMemberEntity));
    }

    public MailingGroupModel updateGroup(MailingGroupModel mailingGroupModel) throws MailingGroupNotFoundException {
        MailingGroupEntity mailingGroupEntity = mailingGroupRepo.findById(mailingGroupModel.getId()).orElseThrow(
                () -> new MailingGroupNotFoundException("Группа рассылки не найдена")
        );

        mailingGroupEntity.setName(mailingGroupModel.getName());
        return MailingGroupModel.toModel(mailingGroupRepo.save(mailingGroupEntity));
    }


    public MailingMemberModel updateMember(MailingMemberModel mailingMemberModel) throws MailingMemberNotFoundException {
        MailingMemberEntity mailingMemberEntity = mailingMemberRepo.findById(mailingMemberModel.getId()).orElseThrow(
                () -> new MailingMemberNotFoundException("Участник группы не найден")
        );

        mailingMemberEntity.setName(mailingMemberModel.getName());
        mailingMemberEntity.setEmail(mailingMemberModel.getEmail());

        return MailingMemberModel.toModel(mailingMemberRepo.save(mailingMemberEntity));
    }

    public Long deleteGroup(Long id){
        mailingGroupRepo.deleteById(id);
        return id;
    }

    public Long deleteMember(Long id){
        mailingMemberRepo.deleteById(id);
        return id;
    }

    public List<MailingGroupModel> getAll(){
        List<MailingGroupModel> mailingGroupModels = new ArrayList<>();

        for(MailingGroupEntity mailingGroupEntity : mailingGroupRepo.findAll()){
            mailingGroupModels.add(MailingGroupModel.toModel(mailingGroupEntity));
        }

        return mailingGroupModels;
    }
}
