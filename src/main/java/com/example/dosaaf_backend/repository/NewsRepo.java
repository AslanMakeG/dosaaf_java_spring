package com.example.dosaaf_backend.repository;

import com.example.dosaaf_backend.entity.NewsEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepo extends CrudRepository<NewsEntity, Long> {
    @Query(value="SELECT n.* FROM news n WHERE in_archive = false ORDER BY creation_date_time asc OFFSET ?1 LIMIT ?2", nativeQuery = true)
    List<NewsEntity> findAllByDateASC(Integer page, Integer limit);
    @Query(value="SELECT n.* FROM news n WHERE in_archive = false ORDER BY creation_date_time desc OFFSET ?1 LIMIT ?2", nativeQuery = true)
    List<NewsEntity> findAllByDateDESC(Integer page, Integer limit);

    @Query(value="SELECT COUNT(n.*) FROM news n WHERE in_archive = false and (n.content LIKE %?1% OR n.title LIKE %?1%)", nativeQuery = true)
    Long countByQuery(String query);

    @Query(value="SELECT n.* FROM news n WHERE in_archive = false and (n.content LIKE %?1% OR n.title LIKE %?1%)", nativeQuery = true)
    List<NewsEntity> findByQuery(String query);

    List<NewsEntity> findAll(Sort by);
}
