package com.gbtec.business.api.model.conversors;

import com.gbtec.business.api.model.EmailDTO;
import com.gbtec.business.application.model.EmailEntity;
import com.gbtec.business.application.model.EmailReceiverEntity;
import com.gbtec.business.application.model.EmailState;

import java.util.ArrayList;
import java.util.List;

public class ApiToBusinessConversor {

    public static EmailEntity email(EmailDTO email) {
        final List<EmailReceiverEntity> receivers = new ArrayList<>();
        email.getEmailTo().forEach(emailAddress -> receivers.add(EmailReceiverEntity.builder().emailTo(emailAddress).hidden(false).build()));
        email.getEmailCC().forEach(emailAddress -> receivers.add(EmailReceiverEntity.builder().emailTo(emailAddress).hidden(true).build()));
        return EmailEntity.builder()
                .uuid(email.getEmailId())
                .from(email.getEmailFrom())
                .body(email.getEmailBody())
                .state(emailState(email.getState()))
                .receivers(receivers)
                .build();
    }

    private static EmailState emailState(int state) {
        return switch(state) {
            case 1 -> EmailState.SENDING;
            case 2 -> EmailState.DRAFT;
            case 3 -> EmailState.DELETED;
            case 4 -> EmailState.SPAM;
            default -> throw new IllegalArgumentException("State not found");
        };
    }

    private ApiToBusinessConversor() {
    }
}
