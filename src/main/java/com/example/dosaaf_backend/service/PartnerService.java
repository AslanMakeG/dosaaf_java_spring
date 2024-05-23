package com.example.dosaaf_backend.service;

import com.example.dosaaf_backend.entity.AnnouncementEntity;
import com.example.dosaaf_backend.entity.PartnerEntity;
import com.example.dosaaf_backend.exception.announcement.AnnouncementNotFoundException;
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

    public PartnerEntity update(PartnerCreationModel partnerCreationModel) throws PartnerNotFoundException, IOException {
        PartnerEntity partnerEntity = partnerRepo.findById(partnerCreationModel.getId()).orElseThrow(
                () -> new PartnerNotFoundException("Партнер не найден")
        );

        if(partnerCreationModel.getImage() != null){
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(partnerCreationModel.getImage()
                    .getOriginalFilename()));
            if(fileName.length() > 255){
                int startIndex = fileName.length() - 255;
                fileName = fileName.substring(startIndex);
            }
            partnerEntity.setImage(fileName);
            FileUtil.deleteFile("./partner/" + partnerCreationModel.getId()); //Удалить
            FileUtil.saveFile("partner/" + partnerEntity.getId(),
                    partnerEntity.getImage(), partnerCreationModel.getImage());
        }

        partnerEntity.setLink(partnerCreationModel.getLink());
        partnerEntity.setName(partnerCreationModel.getName());
        partnerEntity = partnerRepo.save(partnerEntity);

        return partnerEntity;
    }

    public Optional<PartnerEntity> getOne(Long id) throws PartnerNotFoundException {

        if(partnerRepo.findById(id).orElse(null) == null){
            throw new PartnerNotFoundException("Такого партнера не существует");
        }
        return partnerRepo.findById(id);
    }

    public List<PartnerEntity> getAll(){
        return (List<PartnerEntity>) partnerRepo.findAllOrderByIdAsc();
    }
}
