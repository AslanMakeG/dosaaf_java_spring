package com.example.dosaaf_backend.repository;

import com.example.dosaaf_backend.entity.NewsEntity;
import com.example.dosaaf_backend.entity.TestEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestRepo extends CrudRepository<TestEntity, Long> {
}
