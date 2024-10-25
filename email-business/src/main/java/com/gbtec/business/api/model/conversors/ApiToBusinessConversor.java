package com.gbtec.business.api.model.conversors;

import com.gbtec.business.api.model.EmailDTO;
import com.gbtec.business.application.model.EmailEntity;
import com.gbtec.business.application.model.EmailReceiverEntity;
import com.gbtec.business.application.model.EmailState;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ApiToBusinessConversor {

    public static EmailEntity email(EmailDTO email) {
        EmailEntity entity = EmailEntity.builder()
                .uuid(email.getEmailId())
                .from(email.getEmailFrom())
                .body(email.getEmailBody())
                .state(emailState(email.getState()))
                .build();
        final List<EmailReceiverEntity> receivers = new ArrayList<>();
        email.getEmailTo().forEach(emailAddress -> receivers.add(EmailReceiverEntity.builder().email(entity).emailTo(emailAddress).hidden(false).build()));
        email.getEmailCC().forEach(emailAddress -> receivers.add(EmailReceiverEntity.builder().email(entity).emailTo(emailAddress).hidden(true).build()));
        entity.setReceivers(receivers);
        return entity;
    }

    private static EmailState emailState(int state) {
        final Optional<EmailState> foundState = EmailState.findById(state);
        if(foundState.isEmpty()) {
            throw new IllegalArgumentException("State not found");
        }
        return foundState.get();
    }

    private ApiToBusinessConversor() {
    }
}
