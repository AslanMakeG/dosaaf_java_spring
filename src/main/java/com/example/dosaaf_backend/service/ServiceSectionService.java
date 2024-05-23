package com.example.dosaaf_backend.service;

import com.example.dosaaf_backend.entity.ServiceSectionEntity;
import com.example.dosaaf_backend.exception.service.ServiceSectionAlreadyExistsException;
import com.example.dosaaf_backend.exception.service.ServiceSectionNotFoundException;
import com.example.dosaaf_backend.model.ServiceModel;
import com.example.dosaaf_backend.model.ServiceSectionModel;
import com.example.dosaaf_backend.repository.ServiceSectionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceSectionService {

    @Autowired
    private ServiceSectionRepo serviceSectionRepo;

    public ServiceSectionEntity create(ServiceSectionEntity serviceSectionEntity) throws ServiceSectionAlreadyExistsException {
        if(serviceSectionRepo.existsByName(serviceSectionEntity.getName())){
            throw new ServiceSectionAlreadyExistsException("Раздел с таким названием уже существует");
        }
        return serviceSectionRepo.save(serviceSectionEntity);
    }

    public Long delete(Long id){
        serviceSectionRepo.deleteById(id);
        return id;
    }

    public ServiceSectionModel update(ServiceSectionModel serviceSectionModel) throws ServiceSectionNotFoundException {
        ServiceSectionEntity serviceSection = serviceSectionRepo.findById(serviceSectionModel.getId()).orElseThrow(
                () -> new ServiceSectionNotFoundException("Раздел не найден")
        );

        serviceSection.setName(serviceSectionModel.getName());

        return ServiceSectionModel.toModel(serviceSectionRepo.save(serviceSection));
    }

    public List<ServiceSectionModel> getSectionsAndServices() {
        List<ServiceSectionModel> sectionsAndServices = new ArrayList<>();

        serviceSectionRepo.findAll(Sort.by(Sort.Direction.ASC, "id")).forEach(serviceSectionEntity -> {
            sectionsAndServices.add(ServiceSectionModel.toModel(serviceSectionEntity));
        });

        return sectionsAndServices;
    }

    public ServiceSectionModel getOne(Long id) throws ServiceSectionNotFoundException {
        return ServiceSectionModel.toModel(serviceSectionRepo.findById(id).orElseThrow(
                () -> new ServiceSectionNotFoundException("Раздел услуг не найден")
        ));
    }
}
