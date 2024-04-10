package com.example.dosaaf_backend.repository;

import com.example.dosaaf_backend.entity.ServiceEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepo extends CrudRepository<ServiceEntity, Long> {
}
