package com.gbtec.email.api.client.business;

import com.gbtec.email.api.client.business.model.EmailDTO;
import com.gbtec.email.api.model.Email;
import com.gbtec.email.api.model.EmailAccount;

import java.util.List;
import java.util.stream.Collectors;

public final class ApiToBusinessConversors {
    public static EmailDTO email(Email email) {
        return EmailDTO.builder()
                .emailId(email.getEmailId())
                .emailFrom(email.getEmailFrom())
                .emailTo(extractEmails(email.getEmailTo()))
                .emailCC(extractEmails(email.getEmailCC()))
                .emailBody(email.getEmailBody())
                .state(email.getState())
                .build();
    }

    private static List<String> extractEmails(List<EmailAccount> emailAccounts) {
        return emailAccounts.stream().map(EmailAccount::getEmail).collect(Collectors.toList());
    }
}
