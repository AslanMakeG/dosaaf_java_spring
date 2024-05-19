package com.example.dosaaf_backend.repository;

import com.example.dosaaf_backend.entity.NewsPicEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsPicRepo extends CrudRepository<NewsPicEntity, Long> {
    public List<NewsPicEntity> findByNewsId(Long id);
    @Modifying
    @Transactional
    public void deleteByNewsId(Long newsId);
}
