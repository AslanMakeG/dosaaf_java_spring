package com.example.dosaaf_backend.repository;

import com.example.dosaaf_backend.entity.RequestEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepo extends CrudRepository<RequestEntity, Long> {
}
