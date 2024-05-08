package com.example.dosaaf_backend.service;

import com.example.dosaaf_backend.entity.PartnerEntity;
import com.example.dosaaf_backend.exception.partner.PartnerNotFoundException;
import com.example.dosaaf_backend.model.PartnerCreationModel;
import com.example.dosaaf_backend.repository.PartnerRepo;
import com.example.dosaaf_backend.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PartnerService {
    @Autowired
    private PartnerRepo partnerRepo;

    public PartnerEntity create(PartnerCreationModel partnerCreationModel) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(partnerCreationModel.getImage()
                .getOriginalFilename()));

        PartnerEntity partnerEntity = new PartnerEntity();
        partnerEntity.setName(partnerCreationModel.getName());
        partnerEntity.setLink(partnerCreationModel.getLink());
        partnerEntity.setImage(fileName);

        partnerEntity = partnerRepo.save(partnerEntity);

        FileUtil.saveFile("partner/" + partnerEntity.getId(),
                fileName, partnerCreationModel.getImage());

        return partnerEntity;
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
