package com.example.dosaaf_backend.repository;

import com.example.dosaaf_backend.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends CrudRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByActivationCode(String activationCode);
    Optional<UserEntity> findByForgotPasswordCode(String forgotPasswordCode);

    boolean existsByEmail(String email);
}
