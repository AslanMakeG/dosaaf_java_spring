package com.example.dosaaf_backend.repository;

import com.example.dosaaf_backend.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends CrudRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByActivationCode(String activationCode);
    Optional<UserEntity> findByForgotPasswordCode(String forgotPasswordCode);
    List<UserEntity> findBySubscribedForNewsTrue();
    boolean existsByEmail(String email);

    @Query(value = "select count(*) from users\n" +
            "where registration_date >= CAST(?1 AS DATE) and " +
            "registration_date < (CAST(?1 AS DATE) + CAST('1 day' AS INTERVAL))", nativeQuery = true)
    Long countByRegisterDate(String date);
}
