package com.example.dosaaf_backend.service;

import com.example.dosaaf_backend.entity.ServiceEntity;
import com.example.dosaaf_backend.entity.ServiceSectionEntity;
import com.example.dosaaf_backend.exception.service.ServiceAlreadyExistsException;
import com.example.dosaaf_backend.exception.service.ServiceSectionNotFoundException;
import com.example.dosaaf_backend.model.ServiceModel;
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

    public ServiceModel create(ServiceEntity serviceEntity, Long sectionId) throws ServiceAlreadyExistsException, ServiceSectionNotFoundException {
        //в качестве раздела услуги передается ID, у serviceEntity передается null
        if(serviceRepo.existsByName(serviceEntity.getName())){
            throw new ServiceAlreadyExistsException("Услуга с таким названием уже существует");
        }
        if(!serviceSectionRepo.existsById(sectionId)){
            throw new ServiceSectionNotFoundException("Раздел услуги не найден");
        }
        ServiceSectionEntity serviceSectionEntity = serviceSectionRepo.findById(sectionId).get();
        serviceEntity.setServiceSection(serviceSectionEntity);
        serviceRepo.save(serviceEntity);
        return ServiceModel.toModel(serviceEntity);
    }

    public Long delete(Long id){
        serviceRepo.deleteById(id);
        return id;
    }
}
