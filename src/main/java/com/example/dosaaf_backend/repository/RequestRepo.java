package com.example.dosaaf_backend.repository;

import com.example.dosaaf_backend.entity.RequestEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestRepo extends CrudRepository<RequestEntity, Long> {
    List<RequestEntity> findByUserEmail(String email, Sort date);

    Optional<RequestEntity> findFirstByUserEmail(String email, Sort date);

    @Query(value = "select count(*) from requests\n" +
            "where date >= CAST(?1 AS DATE) and " +
            "date < (CAST(?1 AS DATE) + CAST('1 day' AS INTERVAL))", nativeQuery = true)
    Long countByDate(String date);
}
