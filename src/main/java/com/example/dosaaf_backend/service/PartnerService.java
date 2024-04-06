package com.example.dosaaf_backend.service;

import com.example.dosaaf_backend.entity.PartnerEntity;
import com.example.dosaaf_backend.exception.PartnerNotFoundException;
import com.example.dosaaf_backend.repository.PartnerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PartnerService {
    @Autowired
    private PartnerRepo partnerRepo;

    public PartnerEntity create(PartnerEntity partnerEntity){
        return partnerRepo.save(partnerEntity);
    }

    public Long delete(Long id){
        partnerRepo.deleteById(id);
        return id;
    }

    public PartnerEntity update(PartnerEntity partnerEntity) throws PartnerNotFoundException {
        if(partnerRepo.findById(partnerEntity.getId()).orElse(null) == null){
            throw new PartnerNotFoundException("Такого партнера не существует");
        }
        return partnerRepo.save(partnerEntity);
    }

    public Optional<PartnerEntity> getOne(Long id) throws PartnerNotFoundException {

        if(partnerRepo.findById(id).orElse(null) == null){
            throw new PartnerNotFoundException("Такого партнера не существует");
        }
        return partnerRepo.findById(id);
    }

    public List<PartnerEntity> getAll(){
        return (List<PartnerEntity>) partnerRepo.findAll();
    }
}
