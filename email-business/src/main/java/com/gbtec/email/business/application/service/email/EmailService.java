package com.gbtec.email.business.application.service.email;

import com.gbtec.email.business.application.model.EmailEntity;
import com.gbtec.email.business.application.model.EmailEntityFilter;
import com.gbtec.email.business.application.model.EmailException;
import com.gbtec.email.business.application.model.EmailExceptionType;
import com.gbtec.email.business.application.model.EmailState;
import com.gbtec.email.business.application.repository.email.EmailRepository;
import com.gbtec.email.business.transport.service.email.EmailTransportService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class EmailService {

    public static final Comparator<EmailEntity> EMAIL_PERSIST_ORDER = Comparator.comparing(EmailEntity::getUuid);

    @Autowired
    private EmailRepository repository;

    @Autowired
    private EmailTransportService transportService;

    @Transactional(readOnly = true)
    public Optional<EmailEntity> findByUuid(Long uuid) {
        final Optional<EmailEntity> entity = this.repository.findByUuid(uuid);
        entity.ifPresent(this::initializeLazyProperties);
        return entity;
    }

    @Transactional(rollbackFor = EmailException.class)
    public void create(List<EmailEntity> emails) throws EmailException {
        // Instead of breaking on the first email we can't process we could process all of them and aggregate all the
        // errors in one call
        for (EmailEntity email : emails.stream().sorted(EMAIL_PERSIST_ORDER).toList()) {
            create(email);
        }
    }

    private void create(EmailEntity email) throws EmailException {
        if(this.findByUuid(email.getUuid()).isPresent()) {
            throw new EmailException(EmailExceptionType.UUID_ALREADY_IN_USE, "Uuid already in use");
        }
        final EmailEntity insertedEmail = this.repository.insert(email);

        sendEmailIfNecessary(insertedEmail);
    }

    @Transactional
    public boolean update(List<EmailEntity> emails, boolean checkState) {
        // Instead of breaking on the first email we can't process we could process all of them and aggregate all the
        // errors in one call
        emails.stream().sorted(Comparator.comparing(EmailEntity::getUuid)).forEach(e -> update(e, checkState));
        return true;
    }

    @Transactional
    public boolean update(EmailEntity email, boolean checkState) {
        final Optional<EmailEntity> findEmailResult = this.findByUuid(email.getUuid());
        if(findEmailResult.isEmpty()) {
            throw new IllegalStateException("Could not find an email with the given id");
        }
        final EmailEntity foundEmail = findEmailResult.get();
        if(!foundEmail.getState().isUpdatable() && checkState) {
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
            // FIXME: Validate if the email has some receiver
            transportService.send(insertedEmail);
        }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void processReceived(EmailEntity email) {
        final Optional<EmailEntity> persistedEmail = this.findByUuid(email.getUuid());
        if(persistedEmail.isEmpty()) {
            insertSentEmail(email);
            return;
        }
        if(persistedEmail.get().getLastModifiedTime().isAfter(email.getLastModifiedTime())) {
            log.warn("Email received but skipped processing due to a later-modified persisted email. " +
                    "Received email {} Persisted email {}", email, persistedEmail.get());
            return;
        }
        updateSentEmail(persistedEmail.get(), email);
    }

    private void insertSentEmail(EmailEntity email) {
        email.setState(EmailState.SENT);
        this.repository.insert(email);
    }

    private void updateSentEmail(EmailEntity persistedEmail, EmailEntity receivedEmail) {
        receivedEmail.setState(EmailState.SENT);
        this.repository.update(persistedEmail, receivedEmail);
    }

    @Transactional(readOnly = true)
    public List<EmailEntity> find(EmailEntityFilter filter){
        if(filter.getStates().isPresent() && filter.getStates().getValue().contains(EmailState.SENDING)) {
            filter.getStates().getValue().add(EmailState.SENT);
        }
        final List<EmailEntity> emailEntities = this.repository.find(filter);
        emailEntities.forEach(this::initializeLazyProperties);
        return emailEntities;
    }

    private void initializeLazyProperties(EmailEntity email) {
        Hibernate.initialize(email.getReceivers());
    }
}
