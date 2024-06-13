package com.example.dosaaf_backend.repository;

import com.example.dosaaf_backend.entity.AnswerEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepo extends CrudRepository<AnswerEntity, Long> {
    @Modifying
    @Transactional
    void deleteByQuestionId(Long questionId);
}
