package com.gbtec.email.business.application.repository.email;

import com.gbtec.email.business.application.model.EmailEntity;
import com.gbtec.email.business.application.model.EmailReceiverEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.Set;
import java.util.stream.Collectors;

public class EmailRepositoryCustomImpl implements EmailRepositoryCustom {
    @Autowired
    @Lazy
    private EmailRepository repository;

    @Override
    public EmailEntity insert(EmailEntity email) {
        email.getReceivers().forEach(receiver -> receiver.setEmail(email));
        return this.repository.save(email);
    }

    @Override
    public EmailEntity update(EmailEntity persistedEmail, EmailEntity updatedEmail) {
        persistedEmail.setFrom(updatedEmail.getFrom());
        persistedEmail.setBody(updatedEmail.getBody());
        persistedEmail.setState(updatedEmail.getState());

        final Set<EmailReceiverEntity> receiversToRemove = persistedEmail.getReceivers().stream()
                .filter(r -> updatedEmail.getReceivers().stream().noneMatch(r2 -> r2.sameInfo(r)))
                .collect(Collectors.toSet());
        persistedEmail.getReceivers().removeAll(receiversToRemove);

        updatedEmail.getReceivers().stream()
                .filter(r -> persistedEmail.getReceivers().stream().noneMatch(r2 -> r2.sameInfo(r)))
                .forEach(r -> {
                    r.setEmail(persistedEmail);
                    persistedEmail.getReceivers().add(r);
                });

        return this.repository.save(persistedEmail);
    }
}
