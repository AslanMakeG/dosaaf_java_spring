package com.example.dosaaf_backend.repository;

import com.example.dosaaf_backend.entity.AnnouncementEntity;
import com.example.dosaaf_backend.entity.UserAnswerEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAnswerRepo extends CrudRepository<UserAnswerEntity, Long> {
    @Modifying
    @Transactional
    void deleteByQuestionId(Long questionId);
}
