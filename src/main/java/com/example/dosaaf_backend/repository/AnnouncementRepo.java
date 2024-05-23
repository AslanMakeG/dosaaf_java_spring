package com.example.dosaaf_backend.repository;

import com.example.dosaaf_backend.entity.AnnouncementEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnouncementRepo extends CrudRepository<AnnouncementEntity, Long> {
    List<AnnouncementEntity> findAll(Sort id);
}
