package com.gbtec.business.application.service;

import com.gbtec.business.application.model.EmailEntity;
import com.gbtec.business.application.repository.EmailRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

// FIXME: We should use custom exceptions
@Service
public class EmailApplicationService {

    @Autowired
    private EmailRepository repository;

    public Optional<EmailEntity> findById(Long id) {
        return repository.findByUuid(id);
    }

    @Transactional
    public boolean create(EmailEntity email) {
        if(!email.getState().isUpdatable()) {
            throw new IllegalArgumentException("Given state not allowed");
        }
        email.getReceivers().forEach(receiver -> receiver.setEmail(email));
        repository.save(email);
        return true;
    }


}
