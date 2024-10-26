package com.gbtec.business.application.service;

import com.gbtec.business.application.model.EmailEntity;
import com.gbtec.business.application.model.EmailState;
import com.gbtec.business.application.repository.email.EmailRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

// FIXME: We should use custom exceptions
@Service
public class EmailApplicationService {

    @Autowired
    private EmailRepository repository;

    public Optional<EmailEntity> findByUuid(Long uuid) {
        return this.repository.findByUuid(uuid);
    }

    @Transactional
    public boolean create(EmailEntity email) {
        if(!email.getState().isUpdatable()) {
            throw new IllegalArgumentException("Given state is not allowed");
        }
        if(this.findByUuid(email.getUuid()).isPresent()) {
            throw new IllegalArgumentException("Uuid already in use");
        }
        email.getReceivers().forEach(receiver -> receiver.setEmail(email));
        this.repository.save(email);
        return true;
    }

    @Transactional
    public boolean delete(Long id) {
        final Optional<EmailEntity> foundEmail = this.findByUuid(id);
        if(foundEmail.isPresent()) {
            final EmailEntity email = foundEmail.get();
            email.setState(EmailState.DELETED);
            this.repository.save(email);
        }
        return true;
    }

}
