package com.gbtec.business.api.model.conversors;

import com.gbtec.business.api.model.EmailDTO;
import com.gbtec.business.application.model.EmailEntity;
import com.gbtec.business.application.model.EmailState;

public class BusinessToApiConversor {

    private BusinessToApiConversor() {
    }

    public static EmailDTO email(EmailEntity email) {
        return EmailDTO.builder()
                .emailId(email.getUuid())
                .emailFrom(email.getFrom())
                .emailTo(email.shownReceivers())
                .emailCC(email.hiddenReceivers())
                .emailBody(email.getBody())
                .state(convertState(email.getState()))
                .build();
    }

    private static int convertState(EmailState state) {
        return switch (state) {
            case SENDING, SENT ->  1;
            case DRAFT -> 2;
            case DELETED -> 3;
            case SPAM -> 4;
        };
    }
}
