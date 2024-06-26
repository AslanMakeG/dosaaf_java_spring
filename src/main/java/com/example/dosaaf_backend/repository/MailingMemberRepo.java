package com.example.dosaaf_backend.repository;

import com.example.dosaaf_backend.entity.MailingMemberEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailingMemberRepo extends CrudRepository<MailingMemberEntity, Long> {
}
