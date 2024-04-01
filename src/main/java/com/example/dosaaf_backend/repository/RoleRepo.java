package com.example.dosaaf_backend.repository;

import com.example.dosaaf_backend.entity.RoleEntity;
import com.example.dosaaf_backend.enums.ERole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepo extends CrudRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(ERole name);

}
