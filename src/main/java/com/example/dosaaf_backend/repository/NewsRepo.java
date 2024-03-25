package com.example.dosaaf_backend.repository;

import com.example.dosaaf_backend.entity.NewsEntity;
import org.springframework.data.repository.CrudRepository;

public interface NewsRepo extends CrudRepository<NewsEntity, Long> {
}
