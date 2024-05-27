package com.example.dosaaf_backend.service;

import com.example.dosaaf_backend.entity.AnnouncementEntity;
import com.example.dosaaf_backend.exception.announcement.AnnouncementNotFoundException;
import com.example.dosaaf_backend.model.AnnouncementModel;
import com.example.dosaaf_backend.repository.AnnouncementRepo;
import com.example.dosaaf_backend.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
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

    public AnnouncementEntity create(AnnouncementModel announcementModel) throws IOException {
        AnnouncementEntity announcementEntity = new AnnouncementEntity();
        announcementEntity.setContent(announcementModel.getContent());
        announcementEntity.setTitle(announcementModel.getTitle());
        announcementEntity = announcementRepo.save(announcementEntity);

        if(announcementModel.getImage() != null){
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(announcementModel.getImage()
                    .getOriginalFilename()));
            if(fileName.length() > 255){
                int startIndex = fileName.length() - 255;
                fileName = fileName.substring(startIndex);
            }
            announcementEntity.setImage(fileName);
            FileUtil.saveFile("announcement/" + announcementEntity.getId(),
                    fileName, announcementModel.getImage());
            announcementEntity = announcementRepo.save(announcementEntity);
        }

        return announcementEntity;
    }

    public List<AnnouncementEntity> getAll(){
        return announcementRepo.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public AnnouncementEntity getOne(Long id) throws AnnouncementNotFoundException {
        return announcementRepo.findById(id).orElseThrow(
                () -> new AnnouncementNotFoundException("Анонс не найден")
        );
    }

    public Long delete(Long id) throws IOException, AnnouncementNotFoundException {
        AnnouncementEntity announcementEntity = announcementRepo.findById(id).orElseThrow(
                () -> new AnnouncementNotFoundException("Анонс не найден")
        );

        try {
            if (announcementEntity.getImage() != null) {
                FileUtil.deleteFile("./announcement/" + id); //Удалить
            }
        }
        catch (IOException e){
            announcementRepo.deleteById(id);
        }
        finally {
            announcementRepo.deleteById(id);
        }

        return id;
    }

    public AnnouncementEntity update(AnnouncementModel announcementModel) throws AnnouncementNotFoundException, IOException {
        AnnouncementEntity announcementEntity = announcementRepo.findById(announcementModel.getId()).orElseThrow(
                () -> new AnnouncementNotFoundException("Анонс не найден")
        );
        announcementEntity.setContent(announcementModel.getContent());
        announcementEntity.setTitle(announcementModel.getTitle());

        if(!announcementModel.isSameImage() && announcementModel.getImage() != null){
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(announcementModel.getImage()
                    .getOriginalFilename()));
            if(fileName.length() > 255){
                int startIndex = fileName.length() - 255;
                fileName = fileName.substring(startIndex);
            }
            announcementEntity.setImage(fileName);
            try {
                FileUtil.deleteFile("./announcement/" + announcementEntity.getId()); //Удалить

            }
            catch (IOException e){
                System.out.println(e.getMessage());
            }

            FileUtil.saveFile("announcement/" + announcementEntity.getId(),
                    fileName, announcementModel.getImage());
        }
        else if(!announcementModel.isSameImage() && announcementModel.getImage() == null){
            try {
                FileUtil.deleteFile("./announcement/" + announcementEntity.getId()); //Удалить

            }
            catch (IOException e){
                System.out.println(e.getMessage());
            }
            announcementEntity.setImage(null);
        }

        announcementEntity = announcementRepo.save(announcementEntity);

        return announcementEntity;
    }
}
