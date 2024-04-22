package com.example.dosaaf_backend.repository;

import com.example.dosaaf_backend.entity.ServiceEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServiceRepo extends CrudRepository<ServiceEntity, Long> {
    public Optional<ServiceEntity> findByName(String name);
    public boolean existsByName(String name);
}
