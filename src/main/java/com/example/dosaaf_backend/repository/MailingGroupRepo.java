package com.example.dosaaf_backend.repository;

import com.example.dosaaf_backend.entity.MailingGroupEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MailingGroupRepo extends CrudRepository<MailingGroupEntity, Long> {
    List<MailingGroupEntity> findAll(Sort id);
}
