package com.gbtec.email.api.client.business;

import com.gbtec.email.api.client.business.model.EmailDTO;
import com.gbtec.email.api.model.EmailRequest;
import com.gbtec.email.api.model.EmailAccountRequest;

import java.util.List;
import java.util.stream.Collectors;

public final class ApiToBusinessConversors {
    public static EmailDTO email(EmailRequest email) {
        return EmailDTO.builder()
                .emailId(email.getEmailId())
                .emailFrom(email.getEmailFrom())
                .emailTo(extractEmails(email.getEmailTo()))
                .emailCC(extractEmails(email.getEmailCC()))
                .emailBody(email.getEmailBody())
                .state(email.getState())
                .build();
    }

    private static List<String> extractEmails(List<EmailAccountRequest> emailAccounts) {
        return emailAccounts.stream().map(EmailAccountRequest::getEmail).collect(Collectors.toList());
    }

    private ApiToBusinessConversors() {
    }
}
