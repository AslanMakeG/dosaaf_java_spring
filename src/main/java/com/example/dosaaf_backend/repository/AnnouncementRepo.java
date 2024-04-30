package com.example.dosaaf_backend.repository;

import com.example.dosaaf_backend.entity.AnnouncementEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnouncementRepo extends CrudRepository<AnnouncementEntity, Long> {
}
