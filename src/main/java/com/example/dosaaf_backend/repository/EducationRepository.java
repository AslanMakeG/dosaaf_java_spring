package com.example.dosaaf_backend.repository;

import com.example.dosaaf_backend.entity.AnnouncementEntity;
import com.example.dosaaf_backend.entity.EducationEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EducationRepository extends CrudRepository<EducationEntity, Long> {
    List<EducationEntity> findAll(Sort id);
}
