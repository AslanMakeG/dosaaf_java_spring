package com.example.dosaaf_backend.repository;

import com.example.dosaaf_backend.entity.ServiceSectionEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceSectionRepo extends CrudRepository<ServiceSectionEntity, Long> {
    public boolean existsByName(String name);

    List<ServiceSectionEntity> findAll(Sort id);
}
