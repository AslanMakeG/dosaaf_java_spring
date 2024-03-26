package com.example.dosaaf_backend.repository;

import com.example.dosaaf_backend.entity.NewsPicEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsPicRepo extends CrudRepository<NewsPicEntity, Long> {
    public List<NewsPicEntity> findByNewsId(Long id);
}
