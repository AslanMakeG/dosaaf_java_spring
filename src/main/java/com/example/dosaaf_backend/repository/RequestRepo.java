package com.example.dosaaf_backend.repository;

import com.example.dosaaf_backend.entity.RequestEntity;
import com.example.dosaaf_backend.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepo extends CrudRepository<RequestEntity, Long> {
    List<RequestEntity> findByUserEmail(String email);
}
