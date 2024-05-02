package com.example.dosaaf_backend.service;

import com.example.dosaaf_backend.entity.AnnouncementEntity;
import com.example.dosaaf_backend.exception.announcement.AnnouncementNotFoundException;
import com.example.dosaaf_backend.model.AnnouncementCreationModel;
import com.example.dosaaf_backend.repository.AnnouncementRepo;
import com.example.dosaaf_backend.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
public class AnnouncementService {

    @Autowired
    private AnnouncementRepo announcementRepo;

    @Value("${server.address}")
    private String serverAddress;

    public AnnouncementEntity create(AnnouncementCreationModel announcementCreationModel) throws IOException {

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(announcementCreationModel.getImage()
                .getOriginalFilename()));

        AnnouncementEntity announcementEntity = new AnnouncementEntity();
        announcementEntity.setImage(fileName);
        announcementEntity.setContent(announcementCreationModel.getContent());
        announcementEntity.setTitle(announcementCreationModel.getTitle());
        announcementEntity = announcementRepo.save(announcementEntity);

        if(announcementEntity.getImage() != null){
            FileUtil.saveFile("announcement/" + announcementEntity.getId(),
                    fileName, announcementCreationModel.getImage());
        }

        return announcementEntity;
    }

    public List<AnnouncementEntity> getAll(){
        return (List<AnnouncementEntity>) announcementRepo.findAll();
    }

    public AnnouncementEntity getOne(Long id) throws AnnouncementNotFoundException {
        return announcementRepo.findById(id).orElseThrow(
                () -> new AnnouncementNotFoundException("Анонс не найден")
        );
    }

    public Long delete(Long id) throws IOException {
        FileUtil.deleteFile("./announcement/" + id); //Удалить
        announcementRepo.deleteById(id);
        return id;
    }
}
