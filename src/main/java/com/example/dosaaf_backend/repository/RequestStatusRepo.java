package com.example.dosaaf_backend.repository;

import com.example.dosaaf_backend.entity.RequestStatusEntity;
import com.example.dosaaf_backend.enums.EStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RequestStatusRepo extends CrudRepository<RequestStatusEntity, Long> {
    public Optional<RequestStatusEntity> findByName(EStatus name);
}
