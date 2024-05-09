package com.example.dosaaf_backend.config;

import com.example.dosaaf_backend.entity.QuestionTypeEntity;
import com.example.dosaaf_backend.entity.RequestStatusEntity;
import com.example.dosaaf_backend.entity.RoleEntity;
import com.example.dosaaf_backend.enums.EQuestionType;
import com.example.dosaaf_backend.enums.ERole;
import com.example.dosaaf_backend.enums.EStatus;
import com.example.dosaaf_backend.repository.QuestionTypeRepo;
import com.example.dosaaf_backend.repository.RequestStatusRepo;
import com.example.dosaaf_backend.repository.RoleRepo;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatabaseConfig {

    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private RequestStatusRepo requestStatusRepo;
    @Autowired
    private QuestionTypeRepo questionTypeRepo;

    @PostConstruct
    public void checkEnums(){
        roleRepo.findByName(ERole.ROLE_USER).or(() ->
                java.util.Optional.of(roleRepo.save(new RoleEntity(ERole.ROLE_USER))));
        roleRepo.findByName(ERole.ROLE_ADMIN).or(() ->
                java.util.Optional.of(roleRepo.save(new RoleEntity(ERole.ROLE_ADMIN))));

        requestStatusRepo.findByName(EStatus.STATUS_ACCEPTED).or(() ->
                java.util.Optional.of(requestStatusRepo.save(new RequestStatusEntity(EStatus.STATUS_ACCEPTED))));
        requestStatusRepo.findByName(EStatus.STATUS_EXAMINE).or(() ->
                java.util.Optional.of(requestStatusRepo.save(new RequestStatusEntity(EStatus.STATUS_EXAMINE))));
        requestStatusRepo.findByName(EStatus.STATUS_REJECTED).or(() ->
                java.util.Optional.of(requestStatusRepo.save(new RequestStatusEntity(EStatus.STATUS_REJECTED))));

        questionTypeRepo.findByName(EQuestionType.TYPE_SIMPLE).or(() ->
                java.util.Optional.of(questionTypeRepo.save(new QuestionTypeEntity(EQuestionType.TYPE_SIMPLE))));
        questionTypeRepo.findByName(EQuestionType.TYPE_MULTIPLE).or(() ->
                java.util.Optional.of(questionTypeRepo.save(new QuestionTypeEntity(EQuestionType.TYPE_MULTIPLE))));
        questionTypeRepo.findByName(EQuestionType.TYPE_TEXT).or(() ->
                java.util.Optional.of(questionTypeRepo.save(new QuestionTypeEntity(EQuestionType.TYPE_TEXT))));
    }
}
