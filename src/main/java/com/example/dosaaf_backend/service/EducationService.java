package com.example.dosaaf_backend.service;

import com.example.dosaaf_backend.entity.EducationEntity;
import com.example.dosaaf_backend.exception.announcement.AnnouncementNotFoundException;
import com.example.dosaaf_backend.exception.education.EducationNotFoundException;
import com.example.dosaaf_backend.model.AnnouncementModel;
import com.example.dosaaf_backend.model.EducationModel;
import com.example.dosaaf_backend.repository.EducationRepository;
import com.example.dosaaf_backend.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
public class EducationService {
    @Autowired
    private EducationRepository educationRepo;

    public EducationEntity create(EducationModel educationModel) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(educationModel.getFile()
                .getOriginalFilename()));

        EducationEntity educationEntity = new EducationEntity();
        educationEntity.setName(educationModel.getName());
        educationEntity.setFile(fileName);

        educationEntity = educationRepo.save(educationEntity);

        FileUtil.saveFile("education/" + educationEntity.getId(),
                fileName, educationModel.getFile());

        return educationEntity;
    }

    public Long delete(Long id) throws IOException {
        educationRepo.deleteById(id);
        FileUtil.deleteFile("./education/" + id);
        return id;
    }

    public List<EducationEntity> getAll(){
        return educationRepo.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public EducationEntity getOne(Long id) throws EducationNotFoundException {
        return educationRepo.findById(id).orElseThrow(
                () -> new EducationNotFoundException("Учебный материал не найден")
        );
    }

    public EducationEntity update(EducationModel educationModel) throws EducationNotFoundException, IOException {
        EducationEntity educationEntity = educationRepo.findById(educationModel.getId()).orElseThrow(
                () -> new EducationNotFoundException("Учебный материал не найден")
        );

        educationEntity.setName(educationModel.getName());

        if(educationModel.getFile() != null){
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(educationModel.getFile()
                    .getOriginalFilename()));
            if(fileName.length() > 255){
                int startIndex = fileName.length() - 255;
                fileName = fileName.substring(startIndex);
            }
            educationEntity.setFile(fileName);
            FileUtil.deleteFile("./education/" + educationModel.getId()); //Удалить
            FileUtil.saveFile("education/" + educationEntity.getId(),
                    educationEntity.getFile(), educationModel.getFile());
        }

        educationEntity = educationRepo.save(educationEntity);

        return educationEntity;
    }
}
