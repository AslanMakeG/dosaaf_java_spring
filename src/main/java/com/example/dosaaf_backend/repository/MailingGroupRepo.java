package com.example.dosaaf_backend.repository;

import com.example.dosaaf_backend.entity.MailingGroupEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailingGroupRepo extends CrudRepository<MailingGroupEntity, Long> {
}
