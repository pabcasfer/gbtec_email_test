package com.gbtec.business.application.repository.email;

import com.gbtec.business.application.model.EmailEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmailRepository extends CrudRepository<EmailEntity, Long> {
    @Query("FROM email e WHERE e.uuid=:uuid AND e.state!=com.gbtec.business.application.model.EmailState.DELETED")
    Optional<EmailEntity> findByUuid(@Param("uuid") Long uuid);
}
