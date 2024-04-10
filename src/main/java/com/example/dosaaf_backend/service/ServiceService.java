package com.example.dosaaf_backend.service;

import com.example.dosaaf_backend.entity.ServiceEntity;
import com.example.dosaaf_backend.entity.ServiceSectionEntity;
import com.example.dosaaf_backend.repository.ServiceRepo;
import com.example.dosaaf_backend.repository.ServiceSectionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceService {

    @Autowired
    private ServiceRepo serviceRepo;
    @Autowired
    private ServiceSectionRepo serviceSectionRepo;

    public ServiceEntity create(ServiceEntity serviceEntity, Long sectionId){
        //в качестве раздела услуги передается ID, у serviceEntity передается null
        ServiceSectionEntity serviceSectionEntity = serviceSectionRepo.findById(sectionId).get();
        serviceEntity.setServiceSection(serviceSectionEntity);
        return serviceRepo.save(serviceEntity);
    }
}
