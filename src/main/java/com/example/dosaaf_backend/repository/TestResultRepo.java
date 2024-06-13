package com.example.dosaaf_backend.repository;

import com.example.dosaaf_backend.entity.NewsEntity;
import com.example.dosaaf_backend.entity.TestResultEntity;
import com.example.dosaaf_backend.entity.UserAnswerEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestResultRepo extends CrudRepository<TestResultEntity, Long> {
    Optional<TestResultEntity> findByTestIdOrderByDateDesc(Long id);
    List<TestResultEntity> findByTestIdAndUserId(Long testId, Long userId);
    @Query(value="SELECT t.* FROM test_results t WHERE user_id = ?1 ORDER BY date desc OFFSET 0 LIMIT 4", nativeQuery = true)
    List<TestResultEntity> findLastUserResults(Long user_id);
}
