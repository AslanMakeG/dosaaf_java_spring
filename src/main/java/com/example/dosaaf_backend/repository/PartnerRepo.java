package com.example.dosaaf_backend.repository;

import com.example.dosaaf_backend.entity.PartnerEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerRepo extends CrudRepository<PartnerEntity, Long> {
}
