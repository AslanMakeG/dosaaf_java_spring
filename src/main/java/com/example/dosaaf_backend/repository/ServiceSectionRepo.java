package com.example.dosaaf_backend.repository;

import com.example.dosaaf_backend.entity.ServiceSectionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceSectionRepo extends CrudRepository<ServiceSectionEntity, Long> {
    public boolean existsByName(String name);
}
