package com.example.dosaaf_backend.service;

import com.example.dosaaf_backend.entity.ServiceSectionEntity;
import com.example.dosaaf_backend.repository.ServiceSectionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceSectionService {

    @Autowired
    private ServiceSectionRepo serviceSectionRepo;

    public ServiceSectionEntity create(ServiceSectionEntity serviceSectionEntity){
        return serviceSectionRepo.save(serviceSectionEntity);
    }
}
