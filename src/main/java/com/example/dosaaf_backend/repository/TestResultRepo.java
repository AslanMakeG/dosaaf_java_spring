package com.example.dosaaf_backend.repository;

import com.example.dosaaf_backend.entity.TestResultEntity;
import com.example.dosaaf_backend.entity.UserAnswerEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestResultRepo extends CrudRepository<TestResultEntity, Long> {
    Optional<TestResultEntity> findByTestIdOrderByDateDesc(Long id);
    List<TestResultEntity> findByTestId(Long id);
}
