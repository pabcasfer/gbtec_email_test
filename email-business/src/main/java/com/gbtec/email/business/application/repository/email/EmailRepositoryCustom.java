package com.gbtec.email.business.application.repository.email;

import com.gbtec.email.business.application.model.EmailEntity;

public interface EmailRepositoryCustom {
    EmailEntity insert(EmailEntity email);

    EmailEntity update(EmailEntity persistedEmail, EmailEntity updatedEmail);
}
