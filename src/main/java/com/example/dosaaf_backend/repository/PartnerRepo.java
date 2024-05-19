package com.example.dosaaf_backend.repository;

import com.example.dosaaf_backend.entity.PartnerEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartnerRepo extends CrudRepository<PartnerEntity, Long> {
    @Query(value = "SELECT * FROM partners p ORDER BY p.id ASC", nativeQuery = true)
    List<PartnerEntity> findAllOrderByIdAsc();
}
