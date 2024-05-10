package com.example.dosaaf_backend.repository;

import com.example.dosaaf_backend.entity.QuestionEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepo extends CrudRepository<QuestionEntity, Long> {
    @Modifying
    @Transactional
    void deleteByTestId(Long testId);
}
