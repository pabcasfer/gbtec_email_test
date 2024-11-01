package com.gbtec.email.business.testutils.model;

import com.gbtec.email.business.application.model.EmailEntity;
import com.gbtec.email.business.application.model.EmailReceiverEntity;
import com.gbtec.email.business.application.model.EmailState;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class EmailEntityMother {
    private static final SecureRandom RANDOM = new SecureRandom();

    public static EmailEntity draftFromAddressWithoutReceivers(String address) {
        return EmailEntity.builder()
                .uuid(EntityUtils.randomLongId())
                .from(address)
                .state(EmailState.DRAFT)
                .build();
    }

    public static EmailEntity draftFromAddressWithoutReceivers(Long uuid) {
        return EmailEntity.builder()
                .uuid(uuid)
                .from(EmailConstants.EMAIL_1)
                .state(EmailState.DRAFT)
                .build();
    }

    public static EmailEntity draft() {
        return draft(new ArrayList<>());
    }

    public static EmailEntity draft(List<EmailReceiverEntity> receivers) {
        return EmailEntity.builder()
                .uuid(EntityUtils.randomLongId())
                .from(EmailConstants.EMAIL_1)
                .receivers(receivers)
                .state(EmailState.DRAFT)
                .build();
    }

    private EmailEntityMother() {
    }
}
