package com.example.dosaaf_backend.repository;

import com.example.dosaaf_backend.entity.QuestionTypeEntity;
import com.example.dosaaf_backend.enums.EQuestionType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuestionTypeRepo extends CrudRepository<QuestionTypeEntity, Byte> {
    Optional<QuestionTypeEntity> findByName(EQuestionType questionType);
}
