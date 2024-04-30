package com.example.dosaaf_backend.service;

import com.example.dosaaf_backend.entity.AnnouncementEntity;
import com.example.dosaaf_backend.model.AnnouncementCreationModel;
import com.example.dosaaf_backend.repository.AnnouncementRepo;
import com.example.dosaaf_backend.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Service
public class AnnouncementService {

    @Autowired
    private AnnouncementRepo announcementRepo;

    public AnnouncementEntity create(AnnouncementCreationModel announcementCreationModel) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(announcementCreationModel.getImage()
                                                                                    .getOriginalFilename()));

        AnnouncementEntity announcementEntity = new AnnouncementEntity();
        announcementEntity.setImage(fileName);
        announcementEntity.setContent(announcementCreationModel.getContent());
        announcementEntity.setTitle(announcementCreationModel.getTitle());
        announcementEntity = announcementRepo.save(announcementEntity);

        FileUploadUtil.saveFile("data/announcement/" + announcementEntity.getId(),
                fileName, announcementCreationModel.getImage());
        return announcementEntity;
    }
}
