package com.gbtec.business.api.model.conversors;

import com.gbtec.business.api.model.EmailDTO;
import com.gbtec.business.application.model.EmailEntity;

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
                .state(email.getState().getId())
                .build();
    }
}
