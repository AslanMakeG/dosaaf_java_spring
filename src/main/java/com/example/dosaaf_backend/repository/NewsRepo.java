package com.example.dosaaf_backend.repository;

import com.example.dosaaf_backend.entity.NewsEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepo extends CrudRepository<NewsEntity, Long> {
    @Query(value="SELECT n.* FROM news n offset ?1 limit ?2", nativeQuery = true)
    List<NewsEntity> findAll(Integer page, Integer limit);
    @Query(value="SELECT n.* FROM news n WHERE n.content LIKE %?3% OR n.title LIKE %?3% offset ?1 limit ?2", nativeQuery = true)
    List<NewsEntity> findAll(Integer page, Integer limit, String query);
    @Query(value="SELECT COUNT(n.*) FROM news n WHERE n.content LIKE %?1% OR n.title LIKE %?1%", nativeQuery = true)
    Long countByQuery(String query);

    @Query(value="SELECT n.* FROM news n WHERE n.content LIKE %?1% OR n.title LIKE %?1%", nativeQuery = true)
    List<NewsEntity> findByQuery(String query);
}
