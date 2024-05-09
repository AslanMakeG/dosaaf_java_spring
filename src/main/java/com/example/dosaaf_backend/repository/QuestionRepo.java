package com.example.dosaaf_backend.repository;

import com.example.dosaaf_backend.entity.QuestionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepo extends CrudRepository<QuestionEntity, Long> {
}
