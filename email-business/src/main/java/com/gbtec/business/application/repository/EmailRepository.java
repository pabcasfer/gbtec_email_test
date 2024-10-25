package com.gbtec.business.application.repository;

import com.gbtec.business.application.model.EmailEntity;
import org.springframework.data.repository.CrudRepository;

public interface EmailRepository extends CrudRepository<EmailEntity, Long> {
}
