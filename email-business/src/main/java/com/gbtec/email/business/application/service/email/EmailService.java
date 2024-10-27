package com.gbtec.email.business.application.service.email;

import com.gbtec.email.business.application.model.EmailEntity;
import com.gbtec.email.business.application.model.EmailState;
import com.gbtec.email.business.application.repository.email.EmailRepository;
import com.gbtec.email.business.transport.service.email.EmailTransportService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

// FIXME: We should use custom exceptions
@Service
public class EmailService {

    @Autowired
    private EmailRepository repository;

    @Autowired
    private EmailTransportService transportService;

    public Optional<EmailEntity> findByUuid(Long uuid) {
        return this.repository.findByUuid(uuid);
    }

    @Transactional
    public boolean create(EmailEntity email) {
        if(this.findByUuid(email.getUuid()).isPresent()) {
            throw new IllegalArgumentException("Uuid already in use");
        }
        final EmailEntity insertedEmail = this.repository.insert(email);

        sendEmailIfNecessary(insertedEmail);

        return true;
    }

    @Transactional
    public boolean update(EmailEntity email) {
        final Optional<EmailEntity> findEmailResult = this.findByUuid(email.getUuid());
        if(findEmailResult.isEmpty()) {
            throw new IllegalStateException("Could not find an email with the given id");
        }
        final EmailEntity foundEmail = findEmailResult.get();
        if(!foundEmail.getState().isUpdatable()) {
            throw new IllegalStateException("Given email id cannot be updated");
        }
        final EmailEntity insertedEmail = this.repository.update(foundEmail, email);

        sendEmailIfNecessary(insertedEmail);

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

    private void sendEmailIfNecessary(EmailEntity insertedEmail) {
        if(EmailState.isTransport(insertedEmail.getState())) {
            transportService.send(insertedEmail);
        }
    }
}
