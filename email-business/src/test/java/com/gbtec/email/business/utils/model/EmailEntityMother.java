package com.gbtec.email.business.utils.model;

import com.gbtec.email.business.application.model.EmailEntity;
import com.gbtec.email.business.application.model.EmailState;

import java.security.SecureRandom;

public class EmailEntityMother {
    private static final SecureRandom RANDOM = new SecureRandom();

    public static EmailEntity draftFromAddress(String address) {
        return EmailEntity.builder()
                .uuid(RANDOM.nextLong(1L, 99999L))
                .from(address)
                .state(EmailState.DRAFT)
                .build();
    }

    private EmailEntityMother() {
    }
}
