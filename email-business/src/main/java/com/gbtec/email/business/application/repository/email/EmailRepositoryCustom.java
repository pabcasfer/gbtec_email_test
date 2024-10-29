package com.gbtec.email.business.application.repository.email;

import com.gbtec.email.business.application.model.EmailEntity;
import com.gbtec.email.business.application.model.EmailEntityFilter;

import java.util.List;

public interface EmailRepositoryCustom {
    EmailEntity insert(EmailEntity email);

    EmailEntity update(EmailEntity persistedEmail, EmailEntity updatedEmail);

    List<EmailEntity> find(EmailEntityFilter filter);
}
