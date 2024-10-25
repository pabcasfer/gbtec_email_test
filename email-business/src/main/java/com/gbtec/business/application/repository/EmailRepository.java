package com.gbtec.business.application.repository;

import com.gbtec.business.application.model.EmailEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface EmailRepository extends CrudRepository<EmailEntity, Long> {
    Optional<EmailEntity> findByUuid(Long uuid);
}
